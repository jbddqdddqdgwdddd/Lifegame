package com.victor.model.system;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import com.victor.model.seed.Seed;
import com.victor.model.seed.Symmetric;

//建立board类判断生命活动范围，以及他们的邻居。为系统检测提供标准和规范。
public class Board {
    //获取屏幕大小
    private Dimension size = null;

    //储存细胞数组
    Cell[][] gameBoard;

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            return null;
        }
        return gameBoard[x][y];
    }
    //获取在gameboard上的细胞坐标
    public Cell getCell(Point p) {
        return getCell(p.x, p.y);
    }
    //在gameboard上设置细胞坐标
    public void setCell(Cell c) {
        gameBoard[c.x][c.y] = c;
    }
    //在gameboard上删除细胞坐标
    public void removeCell(Cell c) {
        clearCell(c.x, c.y);
    }

    //清除超出边界的细胞
    public void clearCell(int x, int y) {
        if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) {
            return;
        }
        gameBoard[x][y] = null;
    }
    //获取gameBoard宽度
    public int getWidth() {
        return size.width;
    }
    //获取gameBoard长度
    public int getHeight() {
        return size.height;
    }
    //重置gameBoard
    public void resetBoard() {
        gameBoard = new Cell[getWidth()][getHeight()];
    }
    //获取demension size
    public void setSize(Dimension d) {
        this.size = d;
    }
    //获取是否有邻居细胞存在
    public boolean hasNeighbors(int x, int y) {
        for (int s = -1; s <= 1; s++) {
            for (int t = -1; t <= 1; t++) {
                if (s == 0 && t == 0) {
                    continue;
                }
                if (getCell(x + s, y + t) != null) {
                    return true;
                }
            }
        }
        return false;
    }
    //获取邻居
    public ArrayList<Cell> getNeighbors(int x, int y) {
        //为周围（邻居）新建Arraylist存放
        ArrayList<Cell> surrounding = new ArrayList<Cell>(0);
        for (int s = -1; s <= 1; s++) {
            for (int t = -1; t <= 1; t++) {
                if (s == 0 && t == 0) { //跳过自己
                    continue;
                }
                Cell sp = this.getCell(x + s, y + t);
                //没有邻居存在
                if (sp != null) {
                    surrounding.add(sp);
                }
            }
        }
        return surrounding;
    }
    //上下左右方向获取邻居, 2bit之外
    public ArrayList<Cell> getExtra4Neighbors(int x, int y) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>(0);
        Cell c;
        //如果没有邻居，则增添进boardGame
        c = getCell(x + 2, y);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 2, y);
        if (c != null)
            neighbors.add(c);
        c = getCell(x, y + 2);
        if (c != null)
            neighbors.add(c);
        c = getCell(x, y - 2);
        if (c != null)
            neighbors.add(c);

        return neighbors;
    }
    //上下左右方向获取邻居,1-2bit范围
    public ArrayList<Cell> getExtra12Neighbors(int x, int y) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>(0);

        Cell c;

        c = getCell(x + 2, y);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 2, y);
        if (c != null)
            neighbors.add(c);
        c = getCell(x, y + 2);
        if (c != null)
            neighbors.add(c);
        c = getCell(x, y - 2);
        if (c != null)
            neighbors.add(c);

        c = getCell(x + 2, y + 1);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 2, y + 1);
        if (c != null)
            neighbors.add(c);
        c = getCell(x + 2, y - 1);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 2, y - 1);
        if (c != null)
            neighbors.add(c);

        c = getCell(x + 1, y + 2);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 1, y + 2);
        if (c != null)
            neighbors.add(c);
        c = getCell(x + 1, y - 2);
        if (c != null)
            neighbors.add(c);
        c = getCell(x - 1, y - 2);
        if (c != null)
            neighbors.add(c);

        return neighbors;
    }
    //获取dist距离内的额外的邻居
    public ArrayList<Cell> getExtraNeighbors(int x, int y, int dist) {
        ArrayList<Cell> neighbors = new ArrayList<Cell>(0);
        for (int s = -dist; s <= dist; s++) {
            for (int t = -dist; t <= dist; t++) {
                if ((s == -1 || s == 0 || s == 1)
                        && (t == -1 || t == 0 || t == 1)) {
                    continue;
                }
                Cell sp = this.getCell(x + s, y + t);

                if (sp != null) {
                    neighbors.add(sp);
                }
            }
        }
        return neighbors;
    }
}
