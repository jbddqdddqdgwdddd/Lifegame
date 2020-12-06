package com.victor.panel.gamepanel.handler;

import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

public abstract class Handler {
    private RequiredKey requiredKey;
    
    public Handler(RequiredKey requiredKey) {
        this.requiredKey = requiredKey;
    }
    
    public RequiredKey getRequiredKey() { return requiredKey; }
    public void setRequiredKey(RequiredKey rk) { requiredKey = rk; }
}
