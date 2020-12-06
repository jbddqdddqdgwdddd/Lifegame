package com.victor.model.seed;

import java.awt.*;

//用数组表示种子成长的模式
public class GrowPattern extends SeedGrowPattern {

    protected BitPattern seedPattern;

    protected BitPattern GrowPattern;

    protected Point sproutOffset;

    public GrowPattern() {

        this.seedPattern = new BitPattern(new int[][]
                {{0,0,1},
                        {1,1,0},
                        {0,0,0}},
                true);

        this.growPattern = new BitPattern(new int[][]
                {{0,1,1},
                        {1,1,0},
                        {0,1,0}},
                true);

        this.growOffset = new Point(0,0);

    }
}