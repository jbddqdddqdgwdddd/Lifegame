package com.victor.model.step.lifemode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.victor.model.GameModel;
import com.victor.model.system.Cell;

//生命游戏:激烈竞争模式
public class CompetitiveLife extends SimpleLife {
    int PARTITION_WIDTH = 50;

    public CompetitiveLife(GameModel gameModel) {
        super(gameModel);
    }

    public void updateCells() {
        List<Cell> bornCells = Collections.synchronizedList(new ArrayList<>());
        List<Cell> deadCells = Collections.synchronizedList(new ArrayList<>());

        //将更新单元拆分为多个线程以进行多核CPU处理
        List<Thread> threads = new ArrayList<>();
        for (int partitionStart=0; partitionStart < getBoard().getWidth(); partitionStart+=PARTITION_WIDTH) {
            final int partitionStartFinal = partitionStart;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int x=partitionStartFinal; x < getBoard().getWidth() && x < partitionStartFinal+PARTITION_WIDTH; x++) {
                        for (int y=0; y<getBoard().getHeight(); y++) {
                            updateCell(x, y, bornCells, deadCells);
                        }
                    }
                }
            });
            t.start();
            threads.add(t);
        }
        try {
            for (Thread t : threads) {
                t.join();
            }
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        //在添加单元格之前删除单元格，以避免新生命重复单元格，
        for (Cell c: deadCells) {
            getSystem().removeCell(c);
        }
        for (Cell c: bornCells) {
            getSystem().addCell(c);
        }
    }
}
