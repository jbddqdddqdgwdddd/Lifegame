package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.event.MouseEvent;

public abstract class BackgroundClickHandler extends Handler {
    public BackgroundClickHandler(RequiredKey requiredKey) {
        super(requiredKey);
    }
    
    public void mouseClicked(MouseEvent mouseEvent) { }
    public void mouseDoubleClicked(MouseEvent mouseEvent, boolean mouseOverStable) { }
}
