package com.victor.gamesteplistener;

import com.victor.model.step.GameStep.StepType;
import com.victor.model.step.GameStepEvent;
import com.victor.model.step.GameStepListener;
import com.victor.panel.PanelController;

import javax.swing.*;

//默认游戏监听器
public class DefaultGameStepListener implements GameStepListener {
    PanelController pc;

    public DefaultGameStepListener(PanelController panelController) {
        this.pc = panelController;
    }

    //按步骤进行
    public void stepPerformed(GameStepEvent event) {
        if (event.getStepType() == StepType.STEP_BUNDLE) {
            pc.getImageManager().repaintNewImage();
            if (pc.getGameModel().getTime()%100==0) {
                updateStatsPanelText();
            }
        }
    }

    private void updateStatsPanelText() {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                pc.getStatsPanel().getStatsTextPane().setText(
//                        //pc.getGameModel().getStats().getDisplayText());
//            }
//        });
    }
}
