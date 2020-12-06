package com.victor.model.step;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.seed.SeedFactory.SeedType;
import com.victor.model.utilities.GrowUtility;

//PreReproductionStep在游戏开始时或在我们重置后。
//创建随机种子，并循环使用直到“发现”繁殖为止。
public class ReproductionStep extends Step {
    GameModel gameModel;

    public ReproductionStep(GameModel gameModel) {
        super(gameModel);
    }

    public void perform() {
        if(getSystem().getCreatures().size()<40) {
            if (getTime()%200==0) {
                int lifespan = Math.max(16,getSystem().getdefaultCreaLifespan());
                if (lifespan>getSetting().getInt(Settings.MAX_LIFESPAN)) {
                    lifespan = 15;
                }
                getSystem().setdefaultCreaLifespan(lifespan+1);
            }
        }

        if (getSystem().getCreatures().size()<12) {
            SeedType seedType = SeedType.get(getSetting().getString(Settings.SEED_TYPE));
            GrowUtility.growRandomSeed(seedType, getSystem(), null);
        }
    }
}
