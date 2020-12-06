package com.victor.action;

import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;

//游戏开始action事件
@SuppressWarnings("serial")
public class PlayGameAction extends AbstractAction {
    
    protected PanelController controller;
    
    public PlayGameAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
        
    }
    
    public PlayGameAction(PanelController controller) {
        this(controller, "开始");
    }
    

    //执行启动游戏
    public void actionPerformed(ActionEvent e) {
        setPlayGame(!controller.getGameModel().isPlaying());
    } 

    //设置图标改变
    public void setPlayGame(boolean playGame) {
        if (playGame==false) {
            controller.getGameModel().setPlayGame(false);
            this.putValue(NAME, "开始");
        }
        else {
            controller.getGameModel().setPlayGame(true);
            this.putValue(NAME, "暂停");
        }
    }
}
