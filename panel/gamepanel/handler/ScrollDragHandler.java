package com.victor.panel.gamepanel.handler;

import com.victor.panel.PanelController;
import com.victor.panel.gamepanel.handler.HandlerConsts.RequiredKey;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ScrollDragHandler extends BackgroundDragHandler {
    private PanelController gc;
    private Point lastDragPoint;
    //private static Cursor handCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
    private Cursor lastCursor;
    
    public ScrollDragHandler(PanelController panelController, RequiredKey requiredKey) {
        super(requiredKey);
        this.gc = panelController;
    }
    
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        lastDragPoint = mouseEvent.getPoint();
        lastCursor = gc.getScrollPanel().getCursor();
        gc.getScrollPanel().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void mouseReleased(MouseEvent mouseEvent, boolean mouseOverChanged) {
        lastDragPoint = null;
        gc.getScrollPanel().setCursor(lastCursor);
    }
    
    @Override
    public void mouseDragged(MouseEvent mouseEvent, boolean mouseOverChanged) {
        int deltaX = lastDragPoint.x - mouseEvent.getPoint().x;
        int deltaY = lastDragPoint.y - mouseEvent.getPoint().y;
        lastDragPoint = mouseEvent.getPoint();
        gc.getScrollController().scrollBy(deltaX, deltaY);
    }
}
