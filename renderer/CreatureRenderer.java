package com.victor.renderer;

import java.awt.Graphics2D;

import com.victor.model.GameModel;
import com.victor.model.system.Creature;
import com.victor.renderer.ColorModel;

public abstract class CreatureRenderer {

    protected GameModel gameModel;
    protected BoardRenderer boardRenderer;

    public CreatureRenderer(GameModel gameModel, BoardRenderer boardRenderer) {
        this.gameModel = gameModel;
        this.boardRenderer = boardRenderer;
    }

    public abstract void render(Graphics2D g, Creature o);

    public int getBlockSize() {
        return boardRenderer.getBlockSize();
    }

    public GameModel getGameModel() {
        return gameModel;
    }

    public BoardRenderer getBoardRenderer() {
        return boardRenderer;
    }

    public ColorModel getColorModel() {
        return boardRenderer.getColorModel();
    }
}
