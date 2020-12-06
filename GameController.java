package com.victor;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.victor.gamesteplistener.DefaultGameStepListener;
import com.victor.gamesteplistener.WarmupGameStepListener;
import com.victor.model.GameModel;
import com.victor.panel.PanelController;
//游戏控制器
public class GameController {
    private GameModel gameModel;
    private PanelController panelController;
    private Settings settings;
    protected ReentrantReadWriteLock interactionLock;
//初始化基本设置，创建事件监听连接和界面UI初始化
    public GameController() {
        settings = new Settings();
        interactionLock = new ReentrantReadWriteLock();
        gameModel = new GameModel(settings, interactionLock);
        panelController = new PanelController(this);
        getGameModel().getGameThread().addGameStepListener(new DefaultGameStepListener(panelController));
        getGameModel().getGameThread().addGameStepListener(new WarmupGameStepListener(panelController));
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public ReentrantReadWriteLock getInteractionLock() {
        return interactionLock;
    }

    public Settings getSettings() {
        return settings;
    }
}
