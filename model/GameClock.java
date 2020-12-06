package com.victor.model;

//游戏时钟类， 管理游戏时间
public class GameClock {
    private int time;

    public GameClock() {
        time = 0;
    }

    public int getTime() {
        return time;
    }

    void increment() {
        time++;
    }

    void reset() {
        this.time = 0;
    }
}
