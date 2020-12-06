package com.victor.model.system;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import com.victor.model.seed.Seed;
import com.victor.model.GameClock;

//生物类储存细胞的集合，储存细胞状态集合
public class Creature {
    private int id; //生物序号
    private ArrayList<Cell> cells;  //用动态数组储存细胞数组
    private Creature parent;    //上代的生命组
    private ArrayList<Creature> children;  //动态列表存放后代
    private Gegrp gegrp;  //基因组
    private Seed seed;   //储存的种子

    //利用时钟跟踪细胞的年龄
    public GameClock clock;

    // 生命周期,年龄大于生命周期的生物将死亡，防止生物过剩
    public int lifespan;
    private int born; //生物出生时的时间
    private boolean alive = true;   //生物死活状态
    private int timeOfDeath;    //生物死亡时的时间

    private Point location;
    public int x;
    public int y;

    //记录生物属性
    private CreaAttributes attributes;

    public Creature(int id, GameClock clock, int x, int y, Creature parent, Seed seed) {
        this.id = id;
        this.clock = clock;
        this.born = clock.getTime();
        this.parent = parent;
        this.children = new ArrayList<Creature>();
        this.setLocation(x, y);
        this.seed = seed;
        this.gegrp = new Gegrp();
        this.cells = new ArrayList<Cell>();
        this.timeOfDeath = -1;
        this.attributes = new CreaAttributes(this);

        //如果生物不是初代
        if (parent!=null) {
            parent.addChild(this);
            this.gegrp = parent.getGegrp().clone();
            this.lifespan = parent.lifespan;
        }
    }
    //获取生物序号
    public int getId() {
        return id;
    }
    //设定位置
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        this.location = new Point(x,y);
    }
    //获取位置
    public Point getLocation() {
        return location;
    }
    //获取生物上代
    public Creature getParent() {
        return parent;
    }
    //设定生物上代
    public void setParent(Creature parent) {
        this.parent = parent;
    }
    //获取生物后代
    public ArrayList<Creature> getChildren() {
        return children;
    }
    //添加生物后代
    public void addChild(Creature childCrea) {
        children.add(childCrea);
    }
    //设定种子
    public void setSeed(Seed seed) {
        this.seed = seed;
    }
    //获取种子
    public Seed getSeed() {
        return seed;
    }
    //获取游戏时间
    public GameClock getClock() {
        return clock;
    }
    //获取生命周期
    public int getLifespan() {
        return lifespan;
    }
    //设定生命周期
    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }
    //获取出生时间
    public int getBorn() {
        return born;
    }

    //获取所有生物出生到现在的时间（包括死亡的生物）
    public int getTimeSinceBorn() {
        return getClock().getTime() - this.born;
    }

    //获取生物的年龄
    public int getAge() {
        if (isAlive()) {
            return getTimeSinceBorn();
        }
        else {
            return this.timeOfDeath - this.born;//获取死亡生物已经死亡时间
        }
    }
    //获取生物属性
    public CreaAttributes getAttributes() {
        return attributes;
    }

    public Gegrp getGegrp() {
        return gegrp;
    }
    //列出细胞们
    public List<Cell> getCells() {
        return cells;
    }
    //向动态数组中添加细胞
    public Cell addCell(int x, int y) {
        Cell c = new Cell(x, y, this);
        addCell(c);
        return c;
    }
    //增加已存在的细胞
    public void addCell(Cell c) {
        cells.add(c);
    }

    //为细胞创建单元格，但不创建生命
    public Cell createCell(int x, int y) {
        Cell c = new Cell(x, y, this);
        return c;
    }

    public Cell getCell(int x, int y) {
        for (Cell c: cells) {
            if (c.x ==x && c.y==y) {
                return c;
            }
        }
        return null;
    }

    //移除细胞单元格
    public boolean removeCell(Cell c) {
        return cells.remove(c);
    }

    //返回一个生命拥有的细胞
    public int size() {
        return cells.size();
    }

    public boolean removeFromTerritory(Cell c) {
        boolean result = getAttributes().territory.remove(c);

        return result;
    }
    //改变生命状态
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive && !(getTimeOfDeath()>0);
    }
    //获取死亡时间
    public int getTimeOfDeath() {
        return timeOfDeath;
    }
    //设定死亡时间
    public void setTimeOfDeath(int timeOfDeath) {
        this.timeOfDeath = timeOfDeath;
    }

    public boolean equals(Creature o) {
        return this.id == o.id;
    }
}