package com.victor.action;

import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;

//重置游戏action事件
@SuppressWarnings("serial")
public class ResetGameAction extends AbstractAction {
    
    protected PanelController controller;
    
    public ResetGameAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
        
    }
    
    public ResetGameAction(PanelController controller) {
        this(controller, "重置");
    }
    
    //事件触发
    public void actionPerformed(ActionEvent e) {
        controller.getInteractionLock().writeLock().lock();

        controller.getGameModel().resetGame();

        if(!controller.getGameModel().isPlaying()) {
            controller.getGameToolbar().getStartPauseButton().getAction().putValue(NAME, "开始");
        }
        controller.getInteractionLock().writeLock().unlock();
        controller.getImageManager().repaintNewImage();      
        LoadGegrpAction.colorKind = 0;
    }        
}
