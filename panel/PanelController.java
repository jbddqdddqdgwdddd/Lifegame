package com.victor.panel;

import com.victor.GameController;
import com.victor.Settings;
import com.victor.action.ActionManager;
import com.victor.model.GameModel;
import com.victor.model.seed.SeedFactory.SeedType;
import com.victor.panel.gamepanel.ImageManager;
import com.victor.panel.gamepanel.ImageManager.LogoStyle;
import com.victor.panel.gamepanel.ScrollPanel;
import com.victor.panel.gamepanel.ScrollPanelController;
import com.victor.panel.gamepanel.handler.DefaultHandlerSet;
import com.victor.panel.gamepanel.handler.InteractionHandler;
import com.victor.renderer.BoardRenderer;
import com.victor.renderer.ColorModel.BackgroundTheme;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//面板事件触发
public class PanelController {
    GameController gameController;
    GameFrame gameFrame;
    JSplitPane splitPane;
    ActionManager actionManager;
 
    DisplayControlPanel displayControlPanel;
    RulesControlPanel settingsControlPanel;
    JMenuBar gameMenu;
    GameToolbar gameToolbar;
    BoardRenderer boardRenderer;
        
    ScrollPanelController scrollController;
    ImageManager imageManager;
    InteractionHandler interactionHandler;
    BoardSizeHandler boardSizeHandler;
      
    public PanelController(GameController gameController) {
        this.gameController = gameController;
        this.actionManager = new ActionManager(this);
        this.interactionHandler = new InteractionHandler(this); 
        interactionHandler.setHandlerSet(new DefaultHandlerSet(this));

        this.scrollController = new ScrollPanelController(this);
        this.imageManager = new ImageManager(this,  LogoStyle.Small);
        this.boardSizeHandler = new BoardSizeHandler(this);

        buildPanels();
        initComponents();

        addGeneralListeners();
        addToolbarListeners();
        addDisplayControlPanelListeners();
        addRulesControlPanelListeners();

        updateFromSettings();
    }

    public GameController getGameController() {
        return gameController;
    };   
    
    //管理事件触发
    public ActionManager getActionManager() {
        return actionManager;
    }
    
    public ScrollPanel getScrollPanel() {
        return getScrollController().getScrollPanel();
    }
    
    public ScrollPanelController getScrollController() {
        return scrollController;
    }
    
    public ImageManager getImageManager() {
        return imageManager;
    }
    
    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }

    public ReentrantReadWriteLock getInteractionLock() {
        return getGameController().getInteractionLock();
    }

    public BoardRenderer getBoardRenderer() {
        return boardRenderer;
    }
          
    public GameModel getGameModel() {
        return getGameController().getGameModel();
    }
    
    public GameFrame getGameFrame() {
        return gameFrame;
    }

    //设置管理面板
    public RulesControlPanel getRulesControlPanel() {
        return settingsControlPanel;
    }
    
    //展示控制面板
    public DisplayControlPanel getDisplayControlPanel() {
        return displayControlPanel;
    }
    //游戏菜单
    public JMenuBar getGameMenu() {
        return gameMenu;
    }
    //工具栏
    public GameToolbar getGameToolbar() {
        return gameToolbar;
    }
    //获取设定
    public Settings getSettings() {
        return getGameController().getSettings();
    }
    //面板大小设定
    public BoardSizeHandler getBoardSizeHandler() {
        return boardSizeHandler;
    }
    //新建面板
    public void buildPanels() {
        gameFrame = new GameFrame(this);
        gameMenu = new GameMenu(this);
        gameFrame.setJMenuBar(gameMenu);
        gameToolbar = new GameToolbar(this);
        
        boardRenderer = new BoardRenderer(getGameModel());
        displayControlPanel = new DisplayControlPanel(this);
        settingsControlPanel = new RulesControlPanel(this);
        ScrollPanel scrollPanel = getScrollPanel();
        
    //  添加面板组件
        JTabbedPane rightPane = new JTabbedPane();
        rightPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        rightPane.addTab("外观", wrapInScrolPane(displayControlPanel));
        rightPane.addTab("模式调整", wrapInScrolPane(settingsControlPanel));

        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(scrollPanel, BorderLayout.CENTER);
        gamePanel.add(gameToolbar, BorderLayout.NORTH);

        splitPane = new JSplitPane();
        splitPane.setResizeWeight(1);
        splitPane.setOneTouchExpandable(true);
        splitPane.setLeftComponent(gamePanel);
        splitPane.setRightComponent(rightPane);

        gameFrame.add(splitPane);
    }
    
    public JScrollPane wrapInScrolPane(JPanel p) {
        JScrollPane sp = new JScrollPane(p);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setBorder(null);
        return sp;
    }
    //初始化组件
    private void initComponents() {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        gameFrame.setVisible(true);
        getGameToolbar().getZoomSlider().setValue(-4);
        getBoardSizeHandler().updateZoomValue(-4);
        getBoardSizeHandler().updateBoardSizeFromImageSize(getScrollPanel().getViewportSize());
        getImageManager().setBackgroundColor(new Color(160,160,160));

        initSeedTypeComboBox();


    }   


    //添加默认监听器
    public void addGeneralListeners() {
        getScrollPanel().enableMouseListeners();

        getBoardSizeHandler().addListeners();
    }
    //添加工具栏监听器
    private void addToolbarListeners() {
        getGameToolbar().getStartPauseButton().setAction(
                getActionManager().getPlayGameAction()); 
        
        getGameToolbar().getStepButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                getInteractionLock().writeLock().lock();
                getGameModel().performGameStep();
                getInteractionLock().writeLock().unlock();
                getImageManager().repaintNewImage();
            }
        });
                
        getGameToolbar().getResetButton().setAction(
                getActionManager().getResetGameAction());

        getGameToolbar().getGifStopRecordingButton().setAction(getActionManager().getExportGifAction());

        getGameToolbar().getZoomSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                getBoardSizeHandler().updateZoomValue(value);

            }
        });
        
        getGameToolbar().getSpeedSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                updateSpeedValue(value);

            }
        });
    }
    //添加展示控制面板监听器
    public void addDisplayControlPanelListeners() {
        DisplayControlPanel dcp = getDisplayControlPanel();
        dcp.getChckbxCellLayer().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getBoardRenderer().setPaintCellLayer(getDisplayControlPanel().getChckbxCellLayer().isSelected());
                getImageManager().repaintNewImage();
            }
        });
        
        dcp.getChckbxGenomeLayer().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getBoardRenderer().setPaintGegrpLayer(getDisplayControlPanel().getChckbxGenomeLayer().isSelected());
                getImageManager().repaintNewImage();
            }
        });
        
        dcp.getChckbxOrgHeadLayer().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getBoardRenderer().setPaintHeadLayer(getDisplayControlPanel().getChckbxOrgHeadLayer().isSelected());
                getImageManager().repaintNewImage();
            }
        });
        
        dcp.getChckbxOrgTailLayer().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getBoardRenderer().setPaintTailLayer(getDisplayControlPanel().getChckbxOrgTailLayer().isSelected());
                getImageManager().repaintNewImage();
            }
        });
        
        dcp.getChckbxOutlineSeeds().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getBoardRenderer().setOutlineSeeds(getDisplayControlPanel().getChckbxOutlineSeeds().isSelected());
                getImageManager().repaintNewImage();
            }
        });

        dcp.getSpinnerTailLength().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                int length = (int) ((JSpinner) arg0.getSource()).getValue();
                getBoardRenderer().getTailRenderer().setTailLength(length);
                getImageManager().repaintNewImage();
            }
        });

        dcp.getChckbxAutoSplitColors().addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean selected = getDisplayControlPanel().getChckbxAutoSplitColors().isSelected();
                getSettings().set(Settings.AUTO_SPLIT_COLORS, selected);
            }
        });
        //调整背景颜色
        ItemListener backgroundThemeListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (dcp.getRdbtnBackgroundWhite().isSelected()) {
                    getSettings().set(Settings.BACKGROUND_THEME, "white");
                    getBoardRenderer().getColorModel().setBackgroundTheme(BackgroundTheme.white);
                } else {
                    getSettings().set(Settings.BACKGROUND_THEME, "black");
                    getBoardRenderer().getColorModel().setBackgroundTheme(BackgroundTheme.black);
                }
                getImageManager().repaintNewImage();
            }
        };
        dcp.getRdbtnBackgroundBlack().addItemListener(backgroundThemeListener);
        dcp.getRdbtnBackgroundWhite().addItemListener(backgroundThemeListener);
        //细胞配色调整
        ItemListener colorModeListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (dcp.getRdbtnMultiColorMode().isSelected()) {
                    getSettings().set(Settings.COLOR_MODEL, "AngleColorModel"); //角度整齐配色
                } else {
                    getSettings().set(Settings.COLOR_MODEL, "SplitColorModel"); //分散颜色配色
                }
                getImageManager().repaintNewImage();
            }
        };
        dcp.getRdbtnMultiColorMode().addItemListener(colorModeListener);
        dcp.getRdbtnTriColorMode().addItemListener(colorModeListener);

        dcp.getSpinnerColorScheme().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                int value = (int) ((JSpinner) arg0.getSource()).getValue();
                int hue = (value-1)%6*60;
                int hueRange = ((value-1)/6)*100+100;
                getSettings().set(Settings.PRIMARY_HUE_DEGREES, hue);
                getSettings().set(Settings.HUE_RANGE, hueRange);
                getImageManager().repaintNewImage();
            }
        });
    }
    //切换细胞竞争模式
    public void addRulesControlPanelListeners() {
        ItemListener lifeModeListener = new ItemListener() {  
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (getRulesControlPanel().getRdbtnFriendly().isSelected()) {
                    getSettings().set(Settings.LIFE_MODE, "friendly");
                }
                else {
                    getSettings().set(Settings.LIFE_MODE, "competitive2");
                }
            }                      
        };
        getRulesControlPanel().getRdbtnFriendly().addItemListener(lifeModeListener);
        getRulesControlPanel().getRdbtnCompetitive2().addItemListener(lifeModeListener);
        getRulesControlPanel().getRdbtnCompetitive1().addItemListener(lifeModeListener);
        
        getRulesControlPanel().getSeedTypeComboBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               getSettings().set(Settings.SEED_TYPE, getRulesControlPanel().getSeedTypeComboBox().getSelectedItem().toString());
                
            }
        });
        
        getRulesControlPanel().getMaxLifespanSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.MAX_LIFESPAN,((JSpinner) arg0.getSource()).getValue());
            }
        });
        
        getRulesControlPanel().getTargetAgeSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.TARGET_LIFESPAN,((JSpinner) arg0.getSource()).getValue());
            }
        });

        getRulesControlPanel().getChildOneParentAgeSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.CHILD_ONE_PARENT_AGE,((JSpinner) arg0.getSource()).getValue());
            }
        });
        
        getRulesControlPanel().getChildTwoParentAgeSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.CHILD_TWO_PARENT_AGE,((JSpinner) arg0.getSource()).getValue());
            }
        });
        
        getRulesControlPanel().getChildThreeParentAgeSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.CHILD_THREE_PARENT_AGE,((JSpinner) arg0.getSource()).getValue());
            }
        });
        
        getRulesControlPanel().getMutationRateSpinner().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                getSettings().set(Settings.MUTATION_RATE,((JSpinner) arg0.getSource()).getValue());
            }
        });
        
        
        ItemListener sproutModeListener = new ItemListener() {  
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (getRulesControlPanel().getRdbtnVisual().isSelected()) {
                    getSettings().set(Settings.SPROUT_DELAYED_MODE, true);
                }
                else if(getRulesControlPanel().getRdbtnFunctional().isSelected()) {
                    getSettings().set(Settings.SPROUT_DELAYED_MODE, false);
                }
            }                      
        };        
        getRulesControlPanel().getRdbtnVisual().addItemListener(sproutModeListener);
        getRulesControlPanel().getRdbtnFunctional().addItemListener(sproutModeListener);      
    }
    //更新设定
    public void updateFromSettings() {
        getRulesControlPanel().getMaxLifespanSpinner().setValue(
                getSettings().getInt(Settings.MAX_LIFESPAN));
        getRulesControlPanel().getTargetAgeSpinner().setValue(
                getSettings().getInt(Settings.TARGET_LIFESPAN));
        getRulesControlPanel().getChildOneParentAgeSpinner().setValue(
                getSettings().getInt(Settings.CHILD_ONE_PARENT_AGE));
        getRulesControlPanel().getChildTwoParentAgeSpinner().setValue(
                getSettings().getInt(Settings.CHILD_TWO_PARENT_AGE));
        getRulesControlPanel().getChildThreeParentAgeSpinner().setValue(
                getSettings().getInt(Settings.CHILD_THREE_PARENT_AGE));
        getRulesControlPanel().getMutationRateSpinner().setValue(
                getSettings().getInt(Settings.MUTATION_RATE));
        getDisplayControlPanel().getSpinnerColorScheme().setValue(
                1 + getSettings().getInt(Settings.PRIMARY_HUE_DEGREES)/60 +
                (getSettings().getInt(Settings.HUE_RANGE)/100-1)%2*6);

        switch (getSettings().getString(Settings.LIFE_MODE)) {
            case "friendly":
                getRulesControlPanel().getRdbtnFriendly().setSelected(true);
                break;
            case "competitive1":
                getRulesControlPanel().getRdbtnCompetitive1().setSelected(true);
                break;
            default:
                getRulesControlPanel().getRdbtnCompetitive2().setSelected(true);
        }

        switch (getSettings().getString(Settings.BACKGROUND_THEME)) {
            case "white":
                getDisplayControlPanel().getRdbtnBackgroundWhite().setSelected(true);
                break;
            default:
                getDisplayControlPanel().getRdbtnBackgroundBlack().setSelected(true);
        }

        switch (getSettings().getString(Settings.COLOR_MODEL)) {
            case "AngleColorModel":
                getDisplayControlPanel().getRdbtnMultiColorMode().setSelected(true);
                break;
            default:
                getDisplayControlPanel().getRdbtnTriColorMode().setSelected(true);
        }

        SeedType seedType = SeedType.get(getSettings().getString(Settings.SEED_TYPE));
        JComboBox<SeedType> seedCb = (JComboBox<SeedType>) getRulesControlPanel().getSeedTypeComboBox();
        if (seedType!=null) {
            seedCb.setSelectedItem(seedType);
        }

        if (getSettings().getBoolean(Settings.SPROUT_DELAYED_MODE)) {
            getRulesControlPanel().getRdbtnVisual().setSelected(true);
        }
        else {
            getRulesControlPanel().getRdbtnFunctional().setSelected(true);
        }
    }
    
    public void setPlayGame(boolean playGame) {
        getActionManager().getPlayGameAction().setPlayGame(playGame);
    }
    

    //初始化种子成长模式（未完成，仅有一个模式）
    public void initSeedTypeComboBox() {
        JComboBox<SeedType> seedCb = (JComboBox<SeedType>) getRulesControlPanel().getSeedTypeComboBox();

        seedCb.addItem(SeedType.test);
    }
        //更新种子设定
    public void updateSpeedValue(int value) {
        int sleepDelay = 1;
        int iterations = 1;
        switch (value) {
            case -5 : sleepDelay = 500; break;
            case -4 : sleepDelay = 100; break;
            case -3 : sleepDelay = 20; break;
            case -2 : sleepDelay = 8; break;
            case -1 : sleepDelay = 4; break;  
            case 0 : break;
            case 1 : iterations = 2; break;
            case 2 : iterations = 4; break;
            case 3 : iterations = 8; break;
            case 4 : iterations = 16; break;
            case 5 : iterations = 32; break;          
        }
        getGameModel().getGameThread().setSleepDelay(sleepDelay);
        getGameModel().getGameThread().setIterationsPerEvent(iterations);
    }
}
