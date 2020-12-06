package com.victor.model.system;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;

//生命属性的类
public class CreaAttributes {public int colorKind = 0;

    // 用哈希散列表存放所有经过的点
    public HashSet<Point> territory;
    public double territoryProduct = 0; //每个步长的区域sum
    public int competitiveScore = 0;
    public int cellSum = 0;     //细胞总和

    public int maxCells;
    public int parentAgeAtBirth;
    public int birthOrder;
    public int collisionCount=0;    //记录发生的交互次数

    public CreaAttributes(Creature o) {
        this.territory = new HashSet<Point>();
        this.maxCells = 0;
        //获取生命上代
        Creature parent = o.getParent();
        if (parent==null) {//没有上代，即为初始生命
            this.colorKind = (new Random()).nextInt(3);
            this.birthOrder = 0;
        }
        else {
            this.colorKind = parent.getAttributes().colorKind;
            this.parentAgeAtBirth = parent.getAge();
            this.birthOrder = parent.getChildren().size();
        }
    }
    //生命互动范围
    public int getTerritorySize() {
        return territory.size();
    }

}
