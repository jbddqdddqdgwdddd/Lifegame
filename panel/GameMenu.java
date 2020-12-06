package com.victor.panel;

import com.victor.Settings;
import com.victor.action.ExportPngAction;
import com.victor.action.LoadGegrpAction;
import com.victor.action.SaveGegrpAction;
import com.victor.model.GameModel;
import com.victor.panel.gamepanel.ScrollPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//游戏菜单界面设计
public class GameMenu extends JMenuBar implements ActionListener {
    PanelController controller;
    
    private JMenu fileMenu;
    private JMenu gameMenu;
    private JMenuItem exitMenuItem;
    private JMenuItem stepGameMenuItem;
    
    private Action enableMutationAction;
    
    public GameMenu(PanelController controller) {
        this.controller = controller;
        initActions();
        initMenu();
    }
    
    private GameModel getGameModel() {
        return controller.getGameModel();
    }    
    //滚动界面
    public ScrollPanel getScrollPanel() {
        return controller.getScrollPanel();
    }
    //初始化菜单
    private void initMenu() {
        fileMenu = new JMenu("文件");
        this.add(fileMenu);
        gameMenu = new JMenu("游戏");
        this.add(gameMenu);

        exitMenuItem = new JMenuItem("退出");
        exitMenuItem.addActionListener(this);
        fileMenu.add(new SaveGegrpAction(controller));
        fileMenu.add(new LoadGegrpAction(controller));
        fileMenu.add(new ExportPngAction(controller));
        fileMenu.add(controller.getActionManager().getExportGifAction());
        fileMenu.add(new JSeparator());
        fileMenu.add(exitMenuItem);

        stepGameMenuItem = new JMenuItem("下一步");
        stepGameMenuItem.addActionListener(this);

        gameMenu.add(controller.getActionManager().getPlayGameAction());
        gameMenu.add(stepGameMenuItem);
        gameMenu.add(controller.getActionManager().getResetGameAction());
        gameMenu.add(this.enableMutationAction);
    }
    //触发事件
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(exitMenuItem)) {
            System.exit(0);
        } else if (ae.getSource().equals(stepGameMenuItem)) {
            controller.getInteractionLock().writeLock().lock();
            getGameModel().performGameStep();
            controller.getInteractionLock().writeLock().unlock();
            controller.getImageManager().repaintNewImage();
        } 
    }
    //初始化事件
    private void initActions() {
        this.enableMutationAction = new AbstractAction("禁止生命变异") {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean enabled = getGameModel().getSetting().getBoolean(Settings.MUTATION_ENABLED);
                getGameModel().getSetting().set(Settings.MUTATION_ENABLED,!enabled);
                enabled=!enabled;
                if (enabled) {
                    this.putValue(NAME, "禁止生命变异");
                }
                else {
                    this.putValue(NAME, "允许生命变异");
                }
            }
        };
    }
}
