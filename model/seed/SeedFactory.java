package com.victor.model.seed;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.victor.model.RotationG;

public class SeedFactory {

    public static enum SeedType {

        test ("Pattern");

        private final String name;

        private SeedType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public static SeedType get(String name) {
            for (SeedType st : SeedType.values()) {
                if (st.toString().equals(name)) {
                    return st;
                }
            }
            return null;
        }


        public boolean isSymmetric8() {
            return true;
        }


        public boolean isSymmetric4() {
            switch (this) {
                default:
                    return false;
            }
        }


        public boolean isSymmetric2() {
            switch (this) {
//                case L2_RPentomino:
//                    return true;
//                case L2B1_RPentomino:
//                    return true;
                default:
                    return false;
            }
        }
    }

    private static HashMap<SeedType, SeedGrowPattern> patterns = new HashMap<SeedType, SeedGrowPattern>();

    static {
        //加入模式
        patterns.put(SeedType.test, new SeedGrowPattern(){
                    {
                        this.seedPattern = new BitPattern(new int[][]
                                {{1,0,0},
                                        {0,1,0},
                                        {1,1,0}},
                                true);

                        this.growPattern = new BitPattern(new int[][]
                                {{1,1,1},
                                        {1,0,1},
                                        {1,0,1}},
                                true);

                        this.growOffset = new Point(1,0);
                    }
                });
    }

    public static java.util.List<Seed> getSeedRotationG(SeedType type) {
        SeedGrowPattern ssp = patterns.get(type);

        if (ssp==null) {
            return null;
        }

        if (type.isSymmetric8()) {
            return getSymmetricRotation(ssp);
        }
        if (type.isSymmetric2()) {
            return get4SeedRotationG(ssp);
        }
        else {
            return get8SeedRotationG(ssp);
        }

    }
    //用list存放对称旋转的种子
    private static java.util.List<Seed> getSymmetricRotation(SeedGrowPattern ssp) {
        ArrayList<Seed> seedRotationG = new ArrayList<Seed>();
        seedRotationG.add(new Symmetric(ssp));
        return seedRotationG;
    }

    private static java.util.List<Seed> get4SeedRotationG(SeedGrowPattern ssp) {
        ArrayList<Seed> seedRotationG = new ArrayList<Seed>();

        seedRotationG.add(new Seed(ssp, RotationG.get(0)));
        seedRotationG.add(new Seed(ssp, RotationG.get(1)));
        seedRotationG.add(new Seed(ssp, RotationG.get(2)));
        seedRotationG.add(new Seed(ssp, RotationG.get(3)));

        return seedRotationG;
    }

    private static List<Seed> get8SeedRotationG(SeedGrowPattern ssp) {
        ArrayList<Seed> seedRotationG = new ArrayList<Seed>();

        seedRotationG.add(new Seed(ssp, RotationG.get(0, false)));
        seedRotationG.add(new Seed(ssp, RotationG.get(1, false)));
        seedRotationG.add(new Seed(ssp, RotationG.get(2, false)));
        seedRotationG.add(new Seed(ssp, RotationG.get(3, false)));

        seedRotationG.add(new Seed(ssp, RotationG.get(0, true)));
        seedRotationG.add(new Seed(ssp, RotationG.get(1, true)));
        seedRotationG.add(new Seed(ssp, RotationG.get(2, true)));
        seedRotationG.add(new Seed(ssp, RotationG.get(3, true)));

        return seedRotationG;
    }
}
