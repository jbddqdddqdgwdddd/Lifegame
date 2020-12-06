package com.victor.panel.gamepanel.handler;

import com.victor.panel.PanelController;

import java.awt.geom.Point2D;

public class MouseOverHandler extends Handler {
    private PanelController gc;
    
    public MouseOverHandler(PanelController panelController) {
        super(null);
        this.gc = panelController;
    }
    
    public void mouseMoved(Point2D.Double mousePosition) {
        updateMouseOver(mousePosition);

        //gc.getImageManager().repaintNewImage();

 
    }
    
    public void mouseExited() {

        gc.getImageManager().repaintNewImage();

    }
    
    protected void updateMouseOver(Point2D.Double mousePosition) {
        /*
        try {
            gc.getInteractionLock().writeLock().lock();
        }
        finally {
            gc.getInteractionLock().writeLock().unlock();
        }
        */
    }
}
