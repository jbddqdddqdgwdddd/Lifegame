package com.victor.model.step;

import java.util.Collection;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Board;
import com.victor.model.system.System;
import com.victor.model.system.Creature;

public abstract class Step {

    GameModel gameModel;

    public Step(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public System getSystem() {
        return gameModel.getSystem();
    }

    public Collection<Creature> getCreatures() {
        return getSystem().getCreatures();
    }

    public Board getBoard() {
        return gameModel.getBoard();
    }

    public int getTime() {
        return getSystem().getTime();
    }

    public Settings getSetting() {
        return getGameModel().getSetting();
    }

    public abstract void perform();

}

