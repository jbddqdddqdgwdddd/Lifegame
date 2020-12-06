package com.victor.model.seed;

import java.awt.Point;
import java.security.ProtectionDomain;
import com.victor.model.RotationG;

//种子的坐标模式（bitpattern）
public class SeedGrowPattern {

    //种子的出生和繁衍模式
    protected BitPattern seedPattern;
    protected BitPattern growPattern;

    //偏移量
    protected Point growOffset;

    public BitPattern getSeedPattern() {
        return seedPattern;
    }
    public BitPattern getGrowPattern() {
        return growPattern;
    }
    public Point getGrowOffset() {
        return growOffset;
    }

    //种子的出生模式
    public SeedGrowPattern() {

        //种子种子模式
        this.seedPattern = new BitPattern(new int[][]
                {{0,0,1},
                {1,1,0},
                {0,0,0}},
                true);

        //繁衍模式
        this.growPattern = new BitPattern(new int[][]
                {{0,1,1},
                {1,1,0},
                {0,1,0}},
                true);
        //偏移量
        this.growOffset = new Point(0,0);
    }

}
