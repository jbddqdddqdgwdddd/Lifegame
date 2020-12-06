package com.victor.gamesteplistener;

import com.victor.model.GameModel;
import com.victor.model.GameThread;
import com.victor.model.step.GameStep.StepType;
import com.victor.model.step.GameStepEvent;
import com.victor.model.step.GameStepListener;
import com.victor.panel.PanelController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

//启动游戏监听器
public class WarmupGameStepListener implements GameStepListener{
    PanelController pc;
    boolean autoAdjustSpeed;
    boolean autoAdjustDisplayLayers;

    public WarmupGameStepListener(PanelController panelController) {
        this.pc = panelController;
        this.autoAdjustSpeed = true;
        this.autoAdjustDisplayLayers = true;
        addPanelListeners();
    }
    //获取是否自动调整速度
    public boolean isAutoAdjustSpeed() {
        return autoAdjustSpeed;
    }
    //设定为自动调整速度
    public void setAutoAdjustSpeed(boolean autoAdjustSpeed) {
        this.autoAdjustSpeed = autoAdjustSpeed;
    }
    //获取是否自动展示层
    public boolean isAutoAdjustDisplayLayers() {
        return autoAdjustDisplayLayers;
    }
    //设置为自动展示层
    public void setAutoAdjustDisplayLayers(boolean autoAdjustDisplayLayers) {
        this.autoAdjustDisplayLayers = autoAdjustDisplayLayers;
    }
    //获取游戏模型
    private GameModel getGameModel() {
        return pc.getGameModel();
    }
    //获取游戏进程
    private GameThread getGameThread() {
        return getGameModel().getGameThread();
    }

    @Override
    public void stepPerformed(GameStepEvent event) {
        if (event.getStepType() == StepType.STEP_BUNDLE) {
            if (isAutoAdjustSpeed()) {
                updateGameThread();
            }
            if (pc.getGameModel().getTime()%100==0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        doWarmup();
                    }
                });
            }
        }
    }
    //启动游戏
    private void doWarmup() {
        if (isAutoAdjustSpeed() && pc.getGameModel().getTime()>5000) {
            switch (getIterationsPerEvent()) {
                case 1: pc.getGameToolbar().getSpeedSlider().setValue(0); break;
                case 2: pc.getGameToolbar().getSpeedSlider().setValue(1); break;
                case 4: pc.getGameToolbar().getSpeedSlider().setValue(2); break;
                case 8: pc.getGameToolbar().getSpeedSlider().setValue(3); break;
            };
            setAutoAdjustSpeed(false);
        }
        if (isAutoAdjustDisplayLayers() && pc.getGameModel().getTime()>=6000) {
            pc.getDisplayControlPanel().getChckbxCellLayer().setSelected(false);
            pc.getDisplayControlPanel().getChckbxGenomeLayer().setSelected(false);
            setAutoAdjustDisplayLayers(false);
        }
    }
    //更新进程
    private void updateGameThread() {
        getGameThread().setSleepDelay(getSleepDelay());
        getGameThread().setIterationsPerEvent(getIterationsPerEvent());
    }
    //获取睡眠时间延迟（已设置）
    private int getSleepDelay() {
        int sleep = 1;

        if (pc.getGameModel().getTime()<100 ) {
            sleep = 800 - (int) (Math.log10(getGameModel().getTime()/13.0+1)*800) ;
        }
        else {
            sleep = Math.max(1, 40-(int) Math.sqrt(getGameModel().getTime()/4));
        }

        if (getGameModel().getTime()<2000 ) {
            sleep = 10;
        }
        else if (getGameModel().getTime()<4000 ) {
            sleep = 8;
        }
        return sleep;
    }
    //获取交互（触发）事件
    public int getIterationsPerEvent() {
        int autoIterations = 1;

        if (getGameModel().getSystem().getCreatures().size() > 120) {
            autoIterations = 2;
        }
        if (getGameModel().getSystem().getCreatures().size() > 180) {
            autoIterations = 4;
        }
        if (getGameModel().getSystem().getCreatures().size() > 240) {
            autoIterations = 8;
        }
        return autoIterations;
    }
    //添加对游戏面板的监听器
    private void addPanelListeners() {
        pc.getGameToolbar().getSpeedSlider().addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                setAutoAdjustSpeed(false);
            }
        });
        ItemListener layerListener = new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                setAutoAdjustDisplayLayers(false);
            }
        };
        pc.getDisplayControlPanel().getChckbxCellLayer().addItemListener(layerListener);
        pc.getDisplayControlPanel().getChckbxGenomeLayer().addItemListener(layerListener);
        pc.getDisplayControlPanel().getChckbxOrgHeadLayer().addItemListener(layerListener);
        pc.getDisplayControlPanel().getChckbxOrgTailLayer().addItemListener(layerListener);
        pc.getDisplayControlPanel().getChckbxOutlineSeeds().addItemListener(layerListener);
    }
}
