package com.victor.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.victor.model.step.GameStep.StepType;
import com.victor.model.step.GameStepEvent;
import com.victor.model.step.GameStepListener;

//游戏线程
public class GameThread {
    private boolean playGame = false;

    Thread innerThread;

    List<GameStepListener> gameStepListeners;

    GameModel gameModel;
    ReentrantReadWriteLock interactionLock;

    int sleepDelay;
    int iterationsPerEvent;

    public GameThread(GameModel gameModel, ReentrantReadWriteLock interactionLock) {
        this.gameModel = gameModel;
        this.gameStepListeners = new ArrayList<>();
        this.interactionLock = interactionLock;

        this.sleepDelay = 0;
        this.iterationsPerEvent = 1;
    }

    //获取游戏模式
    private GameModel getGameModel() {
        return gameModel;
    }

    public void setPlayGame(boolean playGame) {
        this.playGame = playGame;
        if (playGame) {
            new InnerGameThread().start();
        }
    }
    //游戏状态
    public boolean isPlaying() {
        return playGame;
    }
    //获取游戏休眠时间
    public int getSleepDelay() {
        return sleepDelay;
    }
    //设定游戏休眠时间
    public void setSleepDelay(int sleepDelay) {
        this.sleepDelay = sleepDelay;
    }
    //获取交互事件
    public int getIterationsPerEvent() {
        return iterationsPerEvent;
    }
    //初始化交互事件
    public void setIterationsPerEvent(int iterationsPerEvent) {
        this.iterationsPerEvent = iterationsPerEvent;
    }
    //添加事件监听
    public void addGameStepListener(GameStepListener gameStepListener) {
        gameStepListeners.add(gameStepListener);
    }
    //移除事件监听
    public void removeGameStepListener(GameStepListener gameStepListener) {
        gameStepListeners.remove(gameStepListener);
    }
    //事件触发集合
    private void fireStepBundlePerformed()  {
        for (GameStepListener gsl : gameStepListeners) {
            GameStepEvent event = new GameStepEvent(StepType.STEP_BUNDLE);
            gsl.stepPerformed(event);
        }
    }
    private class InnerGameThread extends Thread {
        public void run() {
            while (playGame) {
                try {
                    interactionLock.writeLock().lock();
                    getGameModel().performGameStep();
                    interactionLock.writeLock().unlock();

                    int iterationsPerEvent = getIterationsPerEvent();
                    if (getGameModel().getTime() % iterationsPerEvent == 0) {
                        fireStepBundlePerformed();
                        int sleep = Math.max(1, getSleepDelay());
                        //////如果sleepDelay小于1，则绘画会出现故障；
                        Thread.sleep(sleep);
                    }
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
