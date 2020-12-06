package com.victor.model;


import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.victor.Settings;
import com.victor.model.system.Board;
import com.victor.model.system.System;
import com.victor.model.step.GameStep;
import com.victor.model.step.GameStepListener;
import com.victor.model.utilities.SystemUtility;

/**
 * GameModel contains the game state
 *
 * @author Alex Shapiro
 */
public class GameModel {
    private System system;
    private GameClock clock;
    private GameStep gameStep;
    private GameThread gameThread;
    private Settings setting;

    public GameModel(Settings setting, ReentrantReadWriteLock interactionLock) {
        this.setting = setting;
        this.clock = new GameClock();
        system = new System(clock);
        gameStep = new GameStep(this);
        gameThread = new GameThread(this, interactionLock);

    }

    public System getSystem() {
        return system;
    }

    public Board getBoard() {
        return system.getBoard();
    }

    public int getTime() {
        return clock.getTime();
    }

    public Settings getSetting() {
        return setting;
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    private GameClock getClock() {
        return clock;
    }

    private void incrementTime() {
        clock.increment();
    }

    public void performGameStep() {
        incrementTime();
        gameStep.perform();
    }

    public void resetGame() {
        getSystem().resetCells();
        SystemUtility.pruneEmptyCreatures(getSystem());
        getSystem().clearJustdiedOrgs();
        getClock().reset();
    }

    public void setPlayGame(boolean playGame) {
        gameThread.setPlayGame(playGame);
    }

    public boolean isPlaying() {
        return gameThread.isPlaying();
    }

    public void setGameStepListener(GameStepListener l) {
        if (gameThread == null) {
            return;
        }
        gameThread.addGameStepListener(l);
    }
}
