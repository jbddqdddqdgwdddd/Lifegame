package com.victor.renderer;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.victor.model.GameModel;
import com.victor.model.system.Creature;
import com.victor.model.utilities.StateUtility;

public class GegrpRenderer extends CreatureRenderer {

    public GegrpRenderer(GameModel gameModel, BoardRenderer boardRenderer) {
        super(gameModel, boardRenderer);
    }

    public void render(Graphics2D g, Creature o) {
        for (Creature ch : o.getChildren()) {
            // 如果我们在孩子出生时隐藏了父母，那么看起来父母就是孩子。
            if (ch.isAlive()) {
                return;
            }
        }

        ArrayList<Point> filteredMutationPoints = getFilteredMutationPoints(o);
        int BLOCK_SIZE = getBlockSize();

        double adjx = 0;
        double adjy = 0;
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
            adjx = parent.x+((o.x-parent.x)*scale)-o.x;
            adjy = parent.y+((o.y-parent.y)*scale)-o.y;
        }

        //在黑色突变点下绘制浅色背景
        g.setColor(getGenomeBackgroundColor(o));
        if (BLOCK_SIZE>1) {
            int countP=0;
            for (Point p: filteredMutationPoints) {
                boolean oneSmaller = false;

                if (countP++>4) {
                    oneSmaller=true;
                }

                if (BLOCK_SIZE>1 || !oneSmaller) {
                    paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy, 0,1,oneSmaller);
                    paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy, 1,0,oneSmaller);
                }
                else {
                    paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy, 1,1,oneSmaller);
                }
            }
        }

        //在背景上方绘制突变点
        g.setColor(Color.black);
        int countP=0;
        for (Point p: filteredMutationPoints) {
            boolean oneSmaller = false;

            if (countP++>4) {
                oneSmaller=true;
            }

            if (BLOCK_SIZE>3&&!oneSmaller || BLOCK_SIZE>4) {

                paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy,0,-1, oneSmaller);
                paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy,-1,0, oneSmaller);
            }
            else {
                paintBlock(g,o.x,o.y,p.x,p.y,adjx, adjy,0,0, oneSmaller);
            }
        }
    }

    public void paintBlock(Graphics2D g, int x, int y, int mx, int my, double adjx, double adjy, int dx, int dy, boolean oneSmaller) {

        int BLOCK_SIZE = getBlockSize();
        double mbs = BLOCK_SIZE/3.5;
        if (BLOCK_SIZE>3) {
            mbs = BLOCK_SIZE/4.25;
        }

        int rx = (int) (BLOCK_SIZE*(x+adjx))+(int)(mx*mbs)-dx;
        int ry = (int) (BLOCK_SIZE*(y+adjy))+(int)(my*mbs)-dy;
        int rw = BLOCK_SIZE+dx*2;
        int rh = BLOCK_SIZE+dy*2;

        if (oneSmaller && rw>1) {
            rw-=1;
            rh-=1;
        }

        g.fillRect(rx, ry, rw, rh);
    }


    ArrayList<Point> getFilteredMutationPoints(Creature o) {
        ArrayList<Point> filteredMutationPoints = new ArrayList<Point>();
        for (int age = 0;age<40;age++) {
            List<Point> mutationPoints = StateUtility.getStatePointsAtAge(o, age);
            int timeLimit = 15000;

            for (int i = 0;i<mutationPoints.size();i++) {
                Point p = mutationPoints.get(i);
                //May be slow
                int mutationAge = getGameModel().getSystem().getTime()-o.getGegrp().getState(age, i).getGameTime();
                if(mutationAge<timeLimit) {
                    filteredMutationPoints.add(p);
                }
            }
        }
        return filteredMutationPoints;
    }

    private Color getGenomeBackgroundColor(Creature o) {
        return getColorModel().getGenomeBackgroundColor(o);
    }
}
