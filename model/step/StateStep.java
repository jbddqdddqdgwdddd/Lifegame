package com.victor.model.step;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Cell;
import com.victor.model.system.Gegrp;
import com.victor.model.system.State;
import com.victor.model.system.Creature;
import com.victor.model.utilities.StateUtility;

//状态改变合集
public class StateStep extends Step {
    GameModel gameModel;
    Random random;

    public StateStep(GameModel gameModel) {
        super(gameModel);
        random = new Random();
    }

    //状态改变初始化
    public void perform() {
        doExistingStates();
        if (getGameModel().getSetting().getBoolean(Settings.MUTATION_ENABLED)) {
            addNewStates();
        }
    }
    //确认生存状态
    private void doExistingStates() {
        for (Creature o : getSystem().getCreatures()) {

            int age = o.getAge();

            List<Point> statePoints =  StateUtility.getOffsetStatePointsAtAge(o, age);

            if (statePoints == null) {
                continue;
            }

            for (int pi =0; pi<statePoints.size();pi++) {
                Point p = statePoints.get(pi);


                Cell c = getBoard().getCell(p);
                if (c!=null) {
                    if (c.getCreature()==o) {
                        getSystem().removeCell(c);
                        c.getCreature().removeFromTerritory(c);
                    }
                    else {
                    }
                }
                else {
                }

            }
        }
    }


//在每个时间段添加大约1个状态，为了获得高状态速率和大量生物，添加了多个状态
//并对于低状态率和低数量的生物，随机添加0或1个状态
    int getCreateStateCount() {
        int invStateRate;
        switch (getSetting().getInt(Settings.MUTATION_RATE)) {
            case 1: invStateRate = 1215; break;
            case 2: invStateRate = 810; break;
            case 3: invStateRate = 540; break;
            case 4: invStateRate = 360; break;
            case 5: invStateRate = 240; break;
            case 6: invStateRate = 160; break;
            case 7: invStateRate = 100; break;
            case 8: invStateRate = 70; break;
            case 9: invStateRate = 50; break;
            case 10: invStateRate = 30; break;
            default: return 0;
        }

        // 大量生命
        if(getSystem().getCreatures().size()>=invStateRate) {
            return getSystem().getCreatures().size()/invStateRate;
        }
        // 少量生命
        else if (getSystem().getCreatures().size()<invStateRate/3 ) {
            return random.nextInt(10)==0 ? 1:0;
        }
        // 一般数量生命
        else if (getSystem().getCreatures().size()<invStateRate ) {
            return random.nextInt(3)==0 ? 1:0;
        }

        return 1;
    }

    //生命状态改变时，改变生命周期
    private boolean mutateLifespan(Creature org) {
        int rand3 = random.nextInt(3);

        if (rand3 == 0 ) {
            if(org.lifespan<getSetting().getInt(Settings.MAX_LIFESPAN)) {
                // 增加生命周期
                org.lifespan +=1;
                return true;
            }
        }
        else if (rand3 == 1) {
            // 减少生命周期
            org.lifespan -=1;
            return true;
        }

        return false;
    }

    private void addState(Cell c) {
        Creature org = c.getCreature();
        Gegrp g = org.getGegrp();
        int age = org.getAge();
        int x = c.x - org.x;
        int y = c.y - org.y;

        State m = StateUtility.addState(org, x, y);

        for (Creature childOrg : org.getChildren()) {
            //假装上代在繁衍时就处于这种状态的话
            childOrg.getGegrp().addState(m);
        }
    }

    //随机移除生命的状态
    private boolean removeState(Creature org) {
        Gegrp g = org.getGegrp();
        int age = org.getAge();

        if(g.getStateCountAtAge(age)==0) {
            //如果该生命在现在的年龄没有任何状态时
            return false;
        }

        int indexAtAge = random.nextInt(g.getStateCountAtAge(age));
        State removeM = g.getState(age, indexAtAge);
        g.removeState(removeM);
        for (Creature childOrg : org.getChildren()) {
            //假装上代在繁衍时就处于这种状态的话
            childOrg.getGegrp().removeState(removeM);
        }

        return true;
    }

    private void addNewStates() {
        ArrayList<Cell> cellsFromAllCreatures = getSystem().getCells();
        if (cellsFromAllCreatures.size()==0) {
            return;
        }

        int createStateCount = getCreateStateCount();

        for (int repeat=0; repeat<createStateCount; repeat++) {

            int size = cellsFromAllCreatures.size();
            int stateIndex = random.nextInt(size);

            Cell randomCell = cellsFromAllCreatures.get(stateIndex);

            boolean mutatedLifespan = mutateLifespan(randomCell.getCreature());
            if (mutatedLifespan && random.nextInt(2)==0) {
                //如果我们更改了寿命周期，则这次有50％的机会完成对状态的改变
                continue;
            }

            // 与添加状态相比，更可能删除状态
            boolean stateIsAdd = (random.nextInt(7)>=4);

            if (stateIsAdd){
                addState(randomCell);
                //现有状态会删除单元格，因此也要为此对新状态进行操作
                getSystem().removeCell(randomCell);

            }
            else {
                removeState(randomCell.getCreature());
            }
        }

    }

}
