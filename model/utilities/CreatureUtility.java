package com.victor.model.utilities;

import com.victor.model.system.Creature;
import java.util.HashSet;

//用于执行生命繁衍
public class CreatureUtility {
    private static HashSet<Creature> getAncestorsAndMe(Creature c, int dist) {
        HashSet<Creature> ancestors = new HashSet<Creature>();
        for (int d=0;d<=dist;d++) {
            if (c!=null) {
                ancestors.add(c);
                c = c.getParent();
            }
            else {
                break;
            }
        }
        return ancestors;
    }
    //查看生命是否是同组
    public static boolean isFamily(Creature c1, Creature c2, int dist) {
        HashSet<Creature> c1Ancestors = getAncestorsAndMe(c1, dist);
        HashSet<Creature> c2Ancestors = getAncestorsAndMe(c2, dist);
        for (Creature c :c1Ancestors) {
            if (c2Ancestors.contains(c)) {
                return true;
            }
        }
        return false;
    }
}