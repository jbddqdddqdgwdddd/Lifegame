package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.event.MouseEvent;

public abstract class ClickHandler extends Handler {
    public ClickHandler(RequiredKey requiredKey) {
        super(requiredKey);
    }
    
    public void mouseClicked(MouseEvent mouseEvent) { }
    public void mouseDoubleClicked(MouseEvent mouseEvent) { }
}
