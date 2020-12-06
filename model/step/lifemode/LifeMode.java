package com.victor.model.step.lifemode;
import java.util.Collection;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Board;
import com.victor.model.system.Cell;
import com.victor.model.system.System;
import com.victor.model.system.Creature;

//抽象类生命的模式，以此为模板可分支出可供玩家选择的不同的模式
public abstract class LifeMode {
    GameModel gameModel;

    public LifeMode(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public abstract void perform();

    protected abstract boolean keepAlive(Cell c, Collection<Cell> neighbors, int x, int y);

    protected abstract Cell getBorn(Collection<Cell> neighbors, int x, int y);

    protected GameModel getGameModel() {
        return gameModel;
    }


    protected System getSystem() {
        return gameModel.getSystem();
    }

    protected Collection<Creature> getCreatures() {
        return getSystem().getCreatures();
    }

    protected Board getBoard() {
        return gameModel.getBoard();
    }

    protected int getTime() {
        return getSystem().getTime();
    }

    protected Settings getSettings() {
        return getGameModel().getSetting();
    }
}
