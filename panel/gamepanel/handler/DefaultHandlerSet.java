package com.victor.panel.gamepanel.handler;

import com.victor.panel.PanelController;
import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

public class DefaultHandlerSet extends HandlerSet {
    private PanelController panelController;
    
    public DefaultHandlerSet(PanelController panelController) {
        super(panelController);
        this.panelController = panelController;
        addHandlers();
    }
    
    public ScrollDragHandler createScrollDragHandler(RequiredKey requiredKey) {
        return new ScrollDragHandler(panelController, requiredKey);
    }    
    
    @Override
    public void addHandlers() {

        // Background Drag Handlers
        addBackgroundDragHandler(createScrollDragHandler(RequiredKey.None));
                  
        // Mouse Wheel Handlers
        //addMouseWheelHandler(new TestMouseWheelHandler(panelController, RequiredKey.Any));
        
         
    }
}
