package com.victor.model.system;

//状态是由生物出生坐标的位置和时间组成。
//如果生物在状态变化点以下有细胞，则该细胞死亡。

import java.awt.*;

public class State {
    private Point location; //未变化的状态变化点
    int creatureAge; //可发生状态变化的生命的年龄
    int gameTime; //记录状态改变的时间

    public State(Point location, int organismAge, int gameTime) {
        this.location = location;
        this.creatureAge = organismAge;
        this.gameTime = gameTime;
    }
    //获取状态变化点坐标
    public Point getLocation() {
        return location;
    }
    //获取状态变化年龄
    public int getCreatureAge() {
        return creatureAge;
    }
    //获取状态改变的时间
    public int getGameTime() {
        return gameTime;
    }
    //哈希码在以后选择位置
    public int hashCode() {
        return location.hashCode();
    }
    //equal判断是否满足状态改变条件
    public boolean equals(Object obj) {
        State s2 = (State) obj;
        return getLocation().equals(s2.getLocation()) && getCreatureAge()==s2.getCreatureAge();
    }
}
