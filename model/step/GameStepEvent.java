package com.victor.model.step;

import com.victor.model.step.GameStep.StepType;
//事件类，回应游戏模式的选择
public class GameStepEvent {

    StepType stepType;

    public GameStepEvent(StepType stepType) {
        this.stepType = stepType;
    }

    public StepType getStepType() {
        return stepType;
    }
}