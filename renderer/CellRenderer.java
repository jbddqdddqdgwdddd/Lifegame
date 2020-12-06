package com.victor.renderer;


import java.awt.Color;
import java.awt.Graphics2D;

import com.victor.model.GameModel;
import com.victor.model.system.Cell;
import com.victor.model.system.Creature;

public class CellRenderer extends CreatureRenderer {
    public CellRenderer(GameModel gameModel, BoardRenderer boardRenderer) {
        super(gameModel, boardRenderer);
    }

    public void render(Graphics2D g, Creature o) {
        int BLOCK_SIZE = getBlockSize();
        if (getBoardRenderer().getOutlineSeeds()) {
            for (Cell c: o.getCells()) {
                if (c.isMarkedAsSeed() || c.getCreature().getAge()<5) {
                    g.setColor(Color.black);
                    g.fillRect(BLOCK_SIZE*c.x-2, BLOCK_SIZE*c.y-2, BLOCK_SIZE+4, BLOCK_SIZE+4);
                }
            }
            for (Cell c: o.getCells()) {
                if (c.isMarkedAsSeed() || c.getCreature().getAge()<5) {
                    g.setColor(Color.white);
                    g.fillRect(BLOCK_SIZE*c.x-1, BLOCK_SIZE*c.y-1, BLOCK_SIZE+2, BLOCK_SIZE+2);
                }
            }
        }

        for (Cell c: o.getCells()) {
            g.setColor(getColor(o));
            g.fillRect(BLOCK_SIZE*c.x, BLOCK_SIZE*c.y, BLOCK_SIZE, BLOCK_SIZE);
        }
    }

    private Color getColor(Creature o) {
        return getColorModel().getCellColor(o);
    }
}