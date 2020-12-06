package com.victor.model.step;


import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Creature;
import com.victor.model.utilities.SystemUtility;

//选择死亡生命的模式
public class JustdieStep extends Step {
    GameModel gameModel;

    public JustdieStep(GameModel gameModel) {
        super(gameModel);
    }

    //模式选择初始化
    public void perform() {
        justdieCreatures();
        pruneEmptyCreatures();
        prunejustdiedCreatures();
        pruneParentTree();
    }

    public void justdieCreatures() {
        List<Creature> creatures = new ArrayList<>(getCreatures());
        for (Creature o : creatures) {
            if (o.getAge()>Math.min(o.lifespan,getSetting().getInt(Settings.MAX_LIFESPAN))) {
                getSystem().justdieCreature(o);
            }
        }
    }
    //调整刚死的生命
    public void prunejustdiedCreatures() {
        Deque<Creature> justdieCrea = getSystem().getjustdieCreatures();
        while (justdieCrea.size()>0) {
            Creature org = justdieCrea.peek();
            if (org.getTimeOfDeath()<getTime()- getSystem().getJustdieTimeSpan()) {
                justdieCrea.poll();
            }
            else {
                break;
            }
        }
    }
    //调整空列表（生命）
    public void pruneEmptyCreatures() {
        SystemUtility.pruneEmptyCreatures(getSystem());
    }

    public void pruneParentTree() {
        for (Creature o : getSystem().getCreatures()) {
            while (o.getParent()!=null) {
                o=o.getParent();
                if (!o.isAlive() && (getTime()-o.getTimeOfDeath()>getSystem().getJustdieTimeSpan())) {
                    o.setParent(null);
                }
            }

        }
    }
}
