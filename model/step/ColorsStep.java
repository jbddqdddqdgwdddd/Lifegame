package com.victor.model.step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Creature;

//可以调整颜色模式
public class ColorsStep extends Step {
    GameModel gameModel;
    int COLOR_COUNT = 3;
    boolean joinMinorColors = false;

    public ColorsStep(GameModel gameModel) {
        super(gameModel);
    }

    public void perform() {
        if (getSetting().getBoolean(Settings.AUTO_SPLIT_COLORS)) {
            splitColors();
        }
    }

    public void setJoinMinorColors(boolean joinMinorColors) {
        this.joinMinorColors = joinMinorColors;
    }

    private void splitColors() {
        if (getSystem().getCreatures().size() ==0) {
            return;
        }
        ArrayList<Entry<Integer,Integer>> kindCount = getColorCounts();
        int largestPercentOfTotal = kindCount.get(kindCount.size()-1).getValue()*100/getSystem().getCreatures().size();

        if (kindCount.get(0).getValue()==0 && largestPercentOfTotal>70) {
            // 如果仅剩2种颜色，并且最大的颜色超过总数的70％，则将其拆分
            splitColor(kindCount.get(kindCount.size()-1).getKey(), kindCount.get(0).getKey());
        }
        else if (joinMinorColors && kindCount.get(1).getValue()>0 && largestPercentOfTotal>80) {
                // 当两种最小的颜色少于总数的20％时，将它们合并在一起
            joinColor(kindCount.get(0).getKey(), kindCount.get(1).getKey());
        }
    }
    //储存颜色配置方案
    private ArrayList<Entry<Integer,Integer>> getColorCounts() {
        HashMap<Integer, Integer> kindCountMap = new HashMap<>();
        for (int k=0;k<COLOR_COUNT;k++) {
            kindCountMap.put(k,0);
        }
        for (Creature o : getSystem().getCreatures()) {
            Integer v = kindCountMap.get(o.getAttributes().colorKind);
            kindCountMap.put(o.getAttributes().colorKind, v == null ? 1 : v+1);
        }
        ArrayList<Entry<Integer,Integer>> kindCount = new ArrayList<>(kindCountMap.entrySet());
        Collections.sort(kindCount, (e1, e2)->(e1.getValue()-e2.getValue()));
        return kindCount;
    }
    //整体颜色设置
    private void joinColor(int fromKind, int toKind) {
        for (Creature o : getSystem().getCreatures()) {
            if (o.getAttributes().colorKind == fromKind) {
                updateCreatureColor(o, toKind);
            }
        }
    }
    //单独的颜色设置
    private void splitColor(int splitKind, int emptyKind) {
        int xSum = 0;
        int sSize = 0;
        for (Creature o : getSystem().getCreatures()) {
            if (o.getAttributes().colorKind == splitKind) {
                xSum += o.x;
                sSize +=1;
            }
        }

        int avgX = xSum / sSize;
        for (Creature o : getSystem().getCreatures()) {
            if (o.getAttributes().colorKind==splitKind) {
                if ((avgX > getBoard().getWidth()/2 && o.x > avgX) ||
                        (avgX <= getBoard().getWidth()/2 && o.x < avgX)) {
                    updateCreatureColor(o, emptyKind);
                }
            }
        }
    }

    private void updateCreatureColor(Creature o, int colorKind) {
        //更改祖先及上代的颜色
        o.getAttributes().colorKind = colorKind;
        Creature p = o.getParent();
        while (p!=null) {
            p.getAttributes().colorKind=colorKind;
            p = p.getParent();
        }
    }
}
