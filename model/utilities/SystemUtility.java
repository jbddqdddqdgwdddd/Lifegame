package com.victor.model.utilities;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;

import com.victor.model.system.Cell;
import com.victor.model.system.System;
import com.victor.model.system.Creature;

//系统工具类：帮助执行系统管理规则
public class SystemUtility {
    public static void updateBoardBounds(System system, Rectangle bounds) {
        system.getBoard().setSize(new Dimension(bounds.width, bounds.height));

        system.getBoard().resetBoard();
        //创建储存被移除的生命的list
        ArrayList<Cell> removeList = new ArrayList<Cell>(0);
        //执行对细胞活动范围的限制
        for (Creature cr : system.getCreatures()) {
            Point crl = cr.getLocation();
            cr.setLocation(crl.x-bounds.x , crl.y-bounds.y);
            for (Cell current : cr.getCells()) {
                if (current.x < bounds.x || current.x >= system.getBoard().getWidth()+bounds.x
                        || current.y < bounds.y || current.y >= system.getBoard().getHeight()+bounds.y) {
                    removeList.add(current);
                }
                else {
                    Point cl = current.getLocation();
                    current.setLocation(new Point(cl.x-bounds.x, cl.y-bounds.y));
                    system.getBoard().setCell(current);
                }
            }
        }
        for (Creature cr : system.getjustdieCreatures()) {
            Point crl = cr.getLocation();
            cr.setLocation(crl.x-bounds.x , crl.y-bounds.y);
        }
        for (Cell r : removeList) {
            system.removeCell(r, true);
        }
        pruneEmptyCreatures(system);
    }

    //细胞生命是否在有效board内
    public static boolean validateBoard(System system) {
        for (Creature cr : system.getCreatures()) {
            for (Cell current : cr.getCells()) {
                if (system.getBoard().getCell(current.x, current.y) != current) {
                    return false;
                }
            }
        }
        for (int i = 0; i < system.getBoard().getWidth(); i++) {
            for (int j = 0; j < system.getBoard().getHeight(); j++) {
                Cell c = system.getBoard().getCell(i, j);
                if (c != null && c.getCreature().getCell(c.x, c.y) != c) {
                    return false;
                }

            }
        }

        return true;
    }
    //控制空（已不存在）的细胞
    public static void pruneEmptyCreatures(System system) {
        HashSet<Creature> prunecreas = new HashSet<Creature>();
        prunecreas.addAll(system.getCreatures());
        for (Creature crea : prunecreas) {
            if (crea.getCells().size() == 0) {
                system.justdieCreature(crea);
            }
        }
    }
}
