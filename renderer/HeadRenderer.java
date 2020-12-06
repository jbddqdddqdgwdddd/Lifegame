package com.victor.renderer;


import java.awt.Color;
import java.awt.Graphics2D;

import com.victor.model.GameModel;
import com.victor.model.system.Creature;

public class HeadRenderer extends CreatureRenderer {

    public HeadRenderer(GameModel gameModel, BoardRenderer boardRenderer) {
        super(gameModel, boardRenderer);
    }

    public void render(Graphics2D g, Creature o) {
        int BLOCK_SIZE = getBlockSize();
        int rectSize = BLOCK_SIZE*5/2;
        g.setColor(getColor(o));
        double ox = o.x;
        double oy = o.y;
        for (Creature ch : o.getChildren()) {
            if (ch.isAlive()) {
                return;
            }
        }

        Creature parent = o.getParent();
        if (parent!=null) {
            int paab = o.getAttributes().parentAgeAtBirth;
            if (parent.getParent()!=null) {
                // 父母和祖父母的平均年龄。
                paab = (paab + o.getParent().getAttributes().parentAgeAtBirth)/2;
            }
            double scale = 1.0*Math.min(o.getAge(),paab)/paab;
            if (o.getChildren().size()>0) {
                scale = 1;
            }
            ox = parent.x+((o.x-parent.x)*scale);
            oy = parent.y+((o.y-parent.y)*scale);
        }

        g.fillRect((int) (BLOCK_SIZE*(ox-1)), (int) (BLOCK_SIZE*(oy-1)), rectSize, rectSize);
    }

    private Color getColor(Creature o) {
        return getColorModel().getHeadColor(o);
    }
}
