package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.event.MouseWheelEvent;

public abstract class MouseWheelHandler extends Handler {
    public MouseWheelHandler(RequiredKey requiredKey) {
        super(requiredKey);
    }
    
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) { }
}
