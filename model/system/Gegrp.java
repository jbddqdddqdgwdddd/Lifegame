package com.victor.model.system;

import java.util.ArrayList;
import java.util.List;


//基因组类, 存放一类生命变化，根据细胞的年龄和环境决定细胞的生死和繁衍
public class Gegrp {
    //利用集合存放生命变化
    List<List<State>> gegrp;
    //初始化新基因组
    public Gegrp() {
        this.gegrp = new ArrayList<>();
    }

    public Gegrp(Gegrp sourceGegrp) {
        this.gegrp = new ArrayList<>();
        // 复制生命变化
        for (int age = 0; age < sourceGegrp.getAgeRange(); age++) {
            List<State> sourceStatesAtAge = sourceGegrp.getStatesAtAge(age);
            ArrayList<State> clonedStatesAtAge = sourceStatesAtAge != null
                    ? new ArrayList<State>(sourceStatesAtAge)
                    : null;
            setStatesAtAge(age, clonedStatesAtAge);
        }
    }
    //复制基因组
    public Gegrp clone() {
        return new Gegrp(this);
    }
    //获取基因组生命状态数量
    public int getAgeRange() {
        return gegrp.size();
    }
    //获取所容纳生命的数量
    public int getStateCountAtAge(int age) {
        if (age >= gegrp.size() || gegrp.get(age) == null) {
            return 0;
        }
        return gegrp.get(age).size();
    }

    public List<State> getStatesAtAge(int age) {
        if (age >= gegrp.size()) {
            return null;
        }
        return gegrp.get(age);
    }

    public void setStatesAtAge(int age, List<State> StatesAtAge) {
        while (gegrp.size()<=age) {
            gegrp.add(null);
        }
        gegrp.set(age, StatesAtAge);
    }

    public State getState(int age, int StateIndex) {
        List<State> StatesAtAge = getStatesAtAge(age);
        if (StatesAtAge == null) {
            return null;
        }
        return StatesAtAge.get(StateIndex);
    }

    public void addState(State m) {
        List<State> StatesAtAge = getStatesAtAge(m.getCreatureAge());
        if (StatesAtAge == null) {
            StatesAtAge = new ArrayList<State>();
            setStatesAtAge(m.getCreatureAge(), StatesAtAge);
        }
        StatesAtAge.add(m);
    }

    public boolean removeState(State m) {
        List<State> StatesAtAge = getStatesAtAge(m.getCreatureAge());
        if (StatesAtAge != null) {
            return StatesAtAge.remove(m);
        }
        return false;
    }
}
