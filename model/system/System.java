package com.victor.model.system;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import com.victor.model.GameClock;
import com.victor.model.seed.Seed;

//系统类，用于管理最近有活动的生命，和我们的gameBoard
public class System {
    //建立list储存生命和最近死去的生命
    List<Creature> creatures;
    Deque<Creature> justdieCreatures;

    Board board;

    int defaultCreaLifespan;
    int justdieTimeSpan;

    GameClock clock;
    //
    public int CreatureId = 0;

    public System(GameClock clock) {
        this.creatures = new ArrayList<Creature>();
        this.justdieCreatures = new ArrayDeque<Creature>();
        this.board = new Board();
        this.clock = clock;
        //设置生命周期
        this.defaultCreaLifespan = 15;
        this.justdieTimeSpan = 1000;
    }
    //获取生命组
    public Collection<Creature> getCreatures() {
        return creatures;
    }
    //获取刚刚死亡的生命组
    public Deque<Creature> getjustdieCreatures() {
        return justdieCreatures;
    }
    //获取gameBoard
    public Board getBoard() {
        return board;
    }
    //获取游戏时钟（记录）
    public GameClock getClock() {
        return clock;
    }
    //获取游戏时间
    public int getTime() {
        return clock.getTime();
    }

    //利用迭代器返回细胞
    public ArrayList<Cell> getCells() {
        ArrayList<Cell> cellList = new ArrayList<Cell>();
        for (Creature o : getCreatures()) {
            cellList.addAll(o.getCells());
        }

        return cellList;
    }
    //重置细胞
    public void resetCells() {
        ArrayList<Cell> cells = new ArrayList<Cell>(getCells());
        for (Cell c : cells) {
            removeCell(c);
        }

        this.setdefaultCreaLifespan(15);
    }
    //查看是否已删除细胞
    public boolean removeCell(Cell c) {
        return removeCell(c, true);
    }

    public boolean removeCell(Cell c, boolean updateBoard) {
        if (c == null) {
            return false;
        }

        boolean result = c.getCreature().removeCell(c);

        if (updateBoard) {
            // We don't want to update the board if we just reset it.
            getBoard().removeCell(c);
        }

        return result;
    }
    //添加细胞
    public Cell addCell(int x, int y, Creature org) {
        Cell c = org.addCell(x, y);
        if (c != null) {
            getBoard().setCell(c);
        }
        return c;
    }

    //添加还未添加的细胞
    public void addCell(Cell c) {
        c.getCreature().addCell(c);
        getBoard().setCell(c);
    }

    //创建细胞，但不将其添加到gameBoard
    public Cell createCell(int x, int y, Creature o) {
        return o.createCell(x, y);
    }

    public void removeCell(int x, int y) {
        Cell c = getBoard().getCell(x, y);
        if (c != null) {
            removeCell(c);
        }
    }
    //设置默认生命周期
    public void setdefaultCreaLifespan(int orgLifespan) {
        this.defaultCreaLifespan = orgLifespan;
    }
    //获取默认生命周期
    public int getdefaultCreaLifespan() {
        return defaultCreaLifespan;
    }
    //设置刚死的细胞的默认生命周期
    public int getJustdieTimeSpan() {
        return justdieTimeSpan;
    }
    //获取去刚死的细胞的默认生命周期
    public void setJustdieTimeSpan(int justdieTimeSpan) {
        this.justdieTimeSpan = justdieTimeSpan;
    }

    public Creature createCreature(int x, int y, Creature parent, Seed seed) {
        CreatureId++;

        Creature newOrg = new Creature(CreatureId, getClock(), x, y, parent, seed);
        if (parent == null) {
            newOrg.setLifespan(getdefaultCreaLifespan());
        }

        creatures.add(newOrg);
        return newOrg;

    }

    public void removeCreature(Creature o) {
        for (Cell c : o.getCells()) {
            getBoard().removeCell(c);
        }
        creatures.remove(o);
    }

    //将刚刚死的生命组单独管理
    public void justdieCreature(Creature o) {
        for (Cell c : o.getCells()) {
            getBoard().removeCell(c);
        }
        o.setAlive(false);
        o.setTimeOfDeath(getTime());

        creatures.remove(o);
        justdieCreatures.add(o);
    }
    //移除刚死的生命组 o
    public void removeJustdied(Creature o) {
        justdieCreatures.remove(o);
    }
    //移除所有的刚死的生命组
    public void clearJustdiedOrgs() {
        getjustdieCreatures().clear();
    }
}