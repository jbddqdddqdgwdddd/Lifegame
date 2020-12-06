package com.victor.model.utilities;

import java.awt.*;
import java.util.List;
import java.util.Random;

import com.victor.model.system.Cell;
import com.victor.model.system.System;
import com.victor.model.system.Creature;
import com.victor.model.seed.Seed;
import com.victor.model.seed.BitPattern;
import com.victor.model.seed.SeedGrowPattern;
import com.victor.model.seed.SeedFactory.SeedType;
import com.victor.model.seed.SeedFactory;

//执行种子成长
public class GrowUtility {
    //种子随机成长，变换位置和状态
    public static Creature growRandomSeed(SeedType seedType, System system, Point location) {
        int x;
        int y;
        if (location!=null) {
            x = location.x;
            y = location.y;
        }
        else {
            x = (new Random()).nextInt(system.getBoard().getWidth());
            y = (new Random()).nextInt(system.getBoard().getHeight());
        }
        //用列表储存状态改变的种子
        List<Seed> seedRotations = SeedFactory.getSeedRotationG(seedType);
        Seed s = seedRotations.get((new Random()).nextInt(seedRotations.size()));

        SeedGrowPattern pattern = s.getSeedGrowPattern();
        final int seedWidth = pattern.getSeedPattern().getWidth();
        final int seedHeight = pattern.getSeedPattern().getWidth();
        final BitPattern currentgrowPattern = pattern.getGrowPattern();
        SeedGrowPattern newPattern = new SeedGrowPattern() {
            {
                this.seedPattern = new BitPattern(new int[seedWidth][seedHeight]);

                this.growPattern = currentgrowPattern;

                this.growOffset = new Point(0, 0);
            }
        };
        //重新定位
        s.setPosition(x, y);
        s.setParentPosition(new Point(120, 120));
        Seed randomSeed = new Seed(newPattern, s.getRotation());

        randomSeed.setPosition(x, y);
        randomSeed.setParentPosition(new Point(x + 1, y + 1));

        return growSeed(randomSeed, null, system);
    }
    //种子成长，改变位置
    public static Creature growSeed(Seed seed, Creature seedCrea, System system) {
        Point growPosition = seed.getGrowPosition();
        Point growCenter = seed.getGrowCenter();

        int seedX = seed.getPosition().x;
        int seedY = seed.getPosition().y;

        int growX = growPosition.x;
        int growY = growPosition.y;

        int newCreaX = growCenter.x;
        int newCreaY = growCenter.y;

        int seedWidth = seed.getSeedWidth();
        int seedHeight = seed.getSeedHeight();

        int growWidth = seed.getGrowWidth();
        int growHeight = seed.getGrowHeight();
        //改变位置
        if (seedX < 0 || seedY < 0
                || seedX + seedWidth > system.getBoard().getWidth() - 1
                || seedY + seedHeight > system.getBoard().getHeight() - 1
                || growX + growWidth > system.getBoard().getWidth() - 1
                || growY + growHeight > system.getBoard().getHeight() - 1
                || growX < 0 || growY < 0) {

            //返回null之前清除种子的标记
            if (seedCrea!=null) {
                for (Cell c : seedCrea.getCells()) {
                    c.setMarkedAsSeed(false);
                }
            }
            return null;
        }

        Creature newCrea = system.createCreature(newCreaX, newCreaY, seedCrea, seed);

        //清除旧种子
        for (int x=0;x<seedWidth;x++) {
            for (int y=0; y<seedHeight;y++) {
                if (seed.getSeedBit(x, y)) {
                    Cell rc = system.getBoard().getCell(seedX+x, seedY+y);

                    system.removeCell(rc);
                }
            }
        }

        for (int si = 0;si<growWidth;si++) {
            for (int sj = 0;sj<growHeight;sj++) {

                int i = growX+si;
                int j = growY+sj;

                Cell c = system.getBoard().getCell(i, j);

                if (c!=null) {
                    //border配置异常时，种子重叠时排错
                    system.removeCell(c);
                    //getBoard().clearCell(i, j);
                }

                if (seed.getGrowBit(si, sj)) {
                    Cell newC = system.addCell(i,j,newCrea);
                }
                else {}
            }
        }
        return newCrea;
    }
}
