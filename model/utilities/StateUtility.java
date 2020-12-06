package com.victor.model.utilities;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.victor.model.system.Gegrp;
import com.victor.model.system.State;
import com.victor.model.system.Creature;
import com.victor.model.RotationG;

//帮助执行生命状态的改变
public class StateUtility {
    //获取当前坐标生命转态年龄
    public static List<Point> getStatePointsAtAge(Creature o, int age) {
        Gegrp gegrp = o.getGegrp();
        if (age >= gegrp.getAgeRange()) {
            return new ArrayList<>();
        }
        List<State> unRotated = gegrp.getStatesAtAge(age);
        if (unRotated == null || unRotated.isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<Point> statePoints = new ArrayList<Point>(unRotated.size());
        for (State m : unRotated) {
            Point rp = RotationG.toBoard(m.getLocation(), o.getSeed().getRotation());
            statePoints.add(rp);
        }
        return statePoints;
    }
    //获取偏移量坐标
    public static List<Point> getOffsetStatePointsAtAge(Creature o, int age) {
        List<Point> statePoints = getStatePointsAtAge(o, age);
        for (Point mp : statePoints) {
            mp.x += o.x;
            mp.y += o.y;
        }
        return statePoints;
    }
    //添加状态
    public static State addState(Creature o, int x, int y) {
        Gegrp gegrp = o.getGegrp();
        Point location = RotationG.fromBoard(new Point(x, y), o.getSeed().getRotation());
        State m = new State(location, o.getAge(), o.getClock().getTime());
        gegrp.addState(m);
        return m;
    }
    //获取生命组最近的状态（gameclock时间段）
    public static Collection<State> getRecentStates(Creature o, int fromTime, int toTime, int maxAge) {
        Gegrp gegrp = o.getGegrp();
        HashSet<State> recentStates = new HashSet<State>();
        for (int age = 0; age<gegrp.getAgeRange(); age++) {
            List<State> mu = gegrp.getStatesAtAge(age);
            if (mu == null) {
                continue;
            }
            for (State m : mu) {
                if (m.getGameTime() >= fromTime && m.getGameTime() <= toTime
                        && m.getCreatureAge() <= maxAge) {
                    recentStates.add(m);
                }
            }
        }
        return recentStates;
    }
}
