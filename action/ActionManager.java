package com.victor.action;

import com.victor.panel.PanelController;

//事件触发合集action
public class ActionManager {
    private PanelController controller;
    
    private PlayGameAction playGameAction;
    private ResetGameAction resetGameAction;
    private ExportGifAction exportGifAction;

    public ActionManager(PanelController controller) {
        this.controller = controller;
    }

    //管控游戏开关
    public PlayGameAction getPlayGameAction() {
        if (playGameAction == null) {
            playGameAction = new PlayGameAction(controller);
        }
        return playGameAction;
    }

    //重置游戏
    public ResetGameAction getResetGameAction() {
        if (resetGameAction == null) {
            resetGameAction = new ResetGameAction(controller);
        }        
        return resetGameAction;
    }
    //导出动图
    public ExportGifAction getExportGifAction() {
        if (exportGifAction == null) {
            exportGifAction = new ExportGifAction(controller);
        }
        return exportGifAction;
    }

}
