package com.victor.model.system;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//细胞类
public class Cell extends Point {

    private Creature Creature = null; //标记生物状态
    public int age; //细胞年龄
    boolean markedAsSeed;  //是否是生长状态

    public Cell(int x, int y, Creature o) {
        super(x, y);
        this.Creature = o;
        this.age = 1;
        this.markedAsSeed = false;
    }
    //获取细胞成长状态
    public boolean isMarkedAsSeed() {
        return markedAsSeed;
    }
    //设置细胞成长状态
    public void setMarkedAsSeed(boolean markedAsSeed) {
        this.markedAsSeed = markedAsSeed;
    }
    //获取细胞生物状态
    public Creature getCreature() {
        return Creature;
    }
    //
    public boolean isSameCreature(Cell c2) {
        return this.getCreature().equals(c2.getCreature());
    }

}
