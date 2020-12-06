package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.event.MouseEvent;

public abstract class BackgroundDragHandler extends Handler {
    public BackgroundDragHandler(RequiredKey requiredKey) {
        super(requiredKey);
    }
    
    public void mousePressed(MouseEvent mouseEvent) { }
    public void mouseReleased(MouseEvent mouseEvent, boolean mouseOverStable) { }
    public void mouseDragged(MouseEvent mouseEvent, boolean mouseOverStable) { }
}
