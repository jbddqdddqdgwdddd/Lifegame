package com.victor.model.step;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.system.Cell;
import com.victor.model.system.Creature;
import com.victor.model.seed.Seed;
import com.victor.model.seed.SeedFactory;
import com.victor.model.seed.SeedFactory.SeedType;
import com.victor.model.utilities.GrowUtility;

//种子生长模式
public class GrowStep extends Step {
    SeedType seedType;
    boolean isSproutDelayedMode = false;
    HashMap<Creature,ArrayList<Seed>> savedSeeds;

    public GrowStep(GameModel gameModel) {
        super(gameModel);
    }

    public void setSeedType(SeedType seedType) {
        this.seedType = seedType;
    }

    public SeedType getSeedType() {
        return seedType;
    }

    //初始化模式
    public void perform() {
        setSeedType(SeedType.get(getSetting().getString(Settings.SEED_TYPE)));
        this.isSproutDelayedMode = getSetting().getBoolean(Settings.SPROUT_DELAYED_MODE);
        if (isSproutDelayedMode) {
            if (this.savedSeeds!=null) {
                sproutSeeds(this.savedSeeds);
            }
            savedSeeds = findSeeds();
        }
        else {
            //增加显示种子的难度
            HashMap<Creature,ArrayList<Seed>> seeds = findSeeds();
            sproutSeeds(seeds);
        }
    }
    //获取上代年龄
    public int getMinParentAge(Creature org, int childNumber) {
        switch (childNumber) {
            case 1: return getSetting().getInt(Settings.CHILD_ONE_PARENT_AGE);
            case 2: return getSetting().getInt(Settings.CHILD_TWO_PARENT_AGE);
            case 3: return getSetting().getInt(Settings.CHILD_THREE_PARENT_AGE);
            default: return 0;
        }
    }
    //检查繁衍是否合法
    public boolean checkMinAgeToHaveChildren(Creature org, int seedCount) {
        int childNumberToBe = seedCount;
        if (org.getChildren()!=null) {
            childNumberToBe += org.getChildren().size();
        }

        // for循环，以防用户设置1个孩子的最小年龄> 2个孩子的最小年龄
        for (int n = 1; n <= childNumberToBe && n <=3; n++) {
            if (org.getAge()+1<getMinParentAge(org,n)) {
                return false;
            }
        }
        return true;
    }

    private void sproutSeeds(HashMap<Creature,ArrayList<Seed>> seeds) {

        for (Creature o: seeds.keySet()) {
            if(!o.isAlive()) {
                continue;
            }
            ArrayList<Seed> seedList = seeds.get(o);

            if (!checkMinAgeToHaveChildren(o, seedList.size())) {
                continue;
            }

            for (Seed s : seedList) {
                Point seedOnPosition = s.getSeedOnPosition();

                Cell c = getBoard().getCell(seedOnPosition);

                if (c==null && !isSproutDelayedMode) {
                    //除非种子重叠，否则几乎永远不会发生。
                    continue;
                }

                GrowUtility.growSeed(s, o, getSystem());

                // update stats
                int childCount = o.getChildren().size()-1;
                if(childCount>=0&&getTime()>100&&childCount<20) { //生长可能失败
                }


            }
        }
    }

    private HashMap<Creature,ArrayList<Seed>> findSeeds() {

        List<Creature> creatures = new ArrayList<>(getSystem().getCreatures());

        HashMap<Creature,ArrayList<Seed>> seeds = new HashMap<Creature,ArrayList<Seed>>();
        //初始化哈希图，因此我们不需要同步的方式通过多个线程添加键
        for (Creature o : creatures) {
            seeds.put(o,new ArrayList<>(5));
        }

        //将查找种子分成多个线程以进行多核CPU处理
        int PARTITION_SIZE = 20;
        List<Thread> threads = new ArrayList<>();
        for (int orgPartition=0; orgPartition<creatures.size();orgPartition+=PARTITION_SIZE) {
            final int orgPartitionFinal = orgPartition;
            Thread t = new Thread(new Runnable() {
                public void run() {
                    for (int oi = orgPartitionFinal; oi<orgPartitionFinal+PARTITION_SIZE && oi<creatures.size();oi++) {
                        Creature o = creatures.get(oi);
                        for (Cell c : o.getCells()) {
                            Seed s = checkAndMarkSeed(c);
                            if (s!=null) {
                                seeds.get(o).add(s);
                            }
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

        return seeds;

    }

    private Seed checkAndMarkSeed(Cell topLeftCell) {
        for (Seed s : SeedFactory.getSeedRotationG(getSeedType())) {

            Point seedOnBit = s.getSeedOnBit();

            int x = topLeftCell.x-seedOnBit.x;
            int y = topLeftCell.y-seedOnBit.y;

            if (x<0||y<0) {
                continue;
            }

            s.setPosition(x, y);
            s.setParentPosition(topLeftCell.getCreature().getLocation());

            if(checkAndMarkSeed(s)) {
                return s;
            }
        }
        return null;

    }

    public boolean checkAndMarkSeed(Seed seed) {

        ArrayList<Cell> seedCells = new ArrayList<Cell>();
        Creature seedOrg = null;
        int i = seed.getPosition().x;
        int j = seed.getPosition().y;
        int seedWidth = seed.getSeedWidth();
        int seedHeight = seed.getSeedHeight();
        int border = seed.getSeedBorder();

        //检查边界
        if( i+seedWidth>=getBoard().getWidth() || j+seedHeight>=getBoard().getHeight()) {
            return false;
        }

        //检查种子
        for (int si=0;si<seedWidth;si++) {
            for (int sj=0;sj<seedHeight;sj++) {
                Cell c = getBoard().getCell(i+si,j+sj);
                if (seed.getSeedBit(si,sj)) {

                    if (c==null) {
                        return false;
                    }
                    if (seedOrg==null) {
                        seedOrg = c.getCreature();
                    }
                    if (!c.getCreature().equals(seedOrg)) {
                        return false;
                    }
                    seedCells.add(c);
                }
                else {
                    if (c!=null) {
                        return false;
                    }
                }
            }
        }

        //检查边界
        for (int si=-border;si<seedWidth+border;si++) {
            for (int sj=-border;sj<seedHeight+border;sj++) {
                if(si<=-1 || sj<=-1 || si>=(seedWidth) || sj>=seedHeight) {
                    if(i+si>=0 && j+sj>=0 &&
                            i+si<=getBoard().getWidth()-1 &&
                            j+sj<=getBoard().getHeight()-1) {
                        if (getBoard().getCell(i+si,j+sj)!=null) {
                            return false;
                        }
                    }
                }
            }
        }

        for (Cell c: seedCells) {
            c.setMarkedAsSeed(true);
        }
        return true;
    }
}
