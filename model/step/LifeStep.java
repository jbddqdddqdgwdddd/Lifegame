package com.victor.model.step;

import com.victor.Settings;
import com.victor.model.GameModel;
import com.victor.model.step.lifemode.LifeMode;
import com.victor.model.step.lifemode.SimpleLife;

//生命游戏模式集合类
public class LifeStep extends Step {
    LifeMode simpleLife;
    LifeMode friendlyLife;
    LifeMode crispLife;
    LifeMode wildLife;
    LifeMode lifeMode;
    LifeMode competitiveLife;

    public LifeStep(GameModel gameModel) {
        super(gameModel);
        simpleLife = new SimpleLife(gameModel);
        lifeMode = simpleLife;
    }
    //选择生命模式
    public void perform() {
        initStats();
        updateLifeMode();
        lifeMode.perform();
    }
    //获取当前生命模式
    public LifeMode getLifeMode() {
        return lifeMode;
    }

    private void updateLifeMode() {
        if("friendly".equals(getSetting().getString(Settings.LIFE_MODE))) {
            this.lifeMode = simpleLife;
        }
        else if("competitive1".equals(getSetting().getString(Settings.LIFE_MODE))) {
            this.lifeMode = competitiveLife;
        }
    }

    private void initStats() {
    }
}
