package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.event.MouseEvent;

public abstract class DragHandler extends Handler {
    public DragHandler(RequiredKey requiredKey) {
        super(requiredKey);
    }
    
    public void mousePressed(MouseEvent mouseEvent) { }
    public void mouseReleased(MouseEvent mouseEvent) { }
    public void mouseDragged(MouseEvent mouseEvent) { }
}
