package com.victor.model.step.lifemode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.victor.model.GameModel;
import com.victor.model.system.Cell;
import com.victor.model.system.Creature;

//生命游戏：简单竞争模式
public class SimpleLife extends LifeMode {

    public SimpleLife(GameModel gameModel) {
        super(gameModel);
    }

    //更新基本配置
    public void perform() {
        updateMetrics();
        updateCells();
    }

    public void updateMetrics() {
        for (Creature o: getSystem().getCreatures()) {
            o.getAttributes().maxCells = Math.max(o.getCells().size(), o.getAttributes().maxCells);
            o.getAttributes().cellSum += o.getCells().size();
            o.getAttributes().competitiveScore = o.getAttributes().cellSum;
        }
    }
    //更新细胞
    public void updateCells() {
        ArrayList<Cell> bornCells = new ArrayList<Cell>();
        ArrayList<Cell> deadCells = new ArrayList<Cell>();

        for (int x = 0; x < getBoard().getWidth(); x++) {
            for (int y = 0; y < getBoard().getHeight(); y++) {
                updateCell(x, y, bornCells, deadCells);
            }
        }

        //在添加单元格之前删除单元格，以避免新生命重复单元格，
        for (Cell c : deadCells) {
            getSystem().removeCell(c);
        }
        for (Cell c : bornCells) {
            getSystem().addCell(c);
        }
    }
    //定义更新细胞的方式
    protected void updateCell(int x, int y, List<Cell> bornCells, List<Cell> deadCells) {
        Cell c = getBoard().getCell(x,y);
        ArrayList<Cell> neighbors = getBoard().getNeighbors(x,y);

        if (c == null) {
            if (neighbors.size()>=3) {
                Cell bc = getBorn(neighbors, x, y);
                if (bc != null) {
                    bornCells.add(bc);
                }
            }
        } else {
            if (!keepAlive(c, neighbors, x, y)) {
                deadCells.add(c);
            } else {
            }
        }
    }
    //生存状态定义
    public boolean keepAlive(Cell c, Collection<Cell> neighbors, int x, int y) {
        if ((neighbors.size() == 2 || neighbors.size() == 3)) {
            c.age += 1;
            return true;
        }
        return false;
    }
    //获取出生条件
    public Cell getBorn(Collection<Cell> neighbors, int x, int y) {
        if (x < 0 || x > getBoard().getWidth() - 1 || y < 0 || y > getBoard().getHeight() - 1) {
            return null;
        }
        if (neighbors.size() != 3) {
            return null;
        }
        // //检查所有邻居是否都来自同一生物
        Creature checkSingleOrg = neighbors.iterator().next().getCreature();
        for (Cell cell : neighbors) {
            if (cell.getCreature() != checkSingleOrg) {
                return null;
            }
        }

        Cell bornCell = getSystem().createCell(x, y, checkSingleOrg);
        return bornCell;
    }
}
