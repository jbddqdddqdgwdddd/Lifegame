package com.victor.model.step;

import com.victor.model.GameModel;

//游戏步进
public class GameStep extends Step {
    public static enum StepType {
        GAME_STEP,
        LIFE_STEP,
        SPROUT_STEP,
        MUTATION_STEP,
        PRUNE_STEP,
        COLOR_STEP,
        STEP_BUNDLE
    }

    LifeStep lifeStep;
    GrowStep sproutStep;
    StateStep mutationStep;
    ReproductionStep preReproductionStep;
    JustdieStep retireAndPruneStep;
    ColorsStep colorsStep;

    public GameStep(GameModel gameModel) {
        super(gameModel);

        lifeStep = new LifeStep(gameModel);
        sproutStep = new GrowStep(gameModel);
        mutationStep = new StateStep(gameModel);
        preReproductionStep = new ReproductionStep(gameModel);
        retireAndPruneStep = new JustdieStep(gameModel);
        colorsStep = new ColorsStep(gameModel);
    }

    //执行默认设置
    public void perform() {
        retireAndPruneStep.perform();
        colorsStep.perform();
        lifeStep.perform();
        mutationStep.perform();
        preReproductionStep.perform();
        sproutStep.perform();

    }



}
