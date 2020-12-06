package com.victor.panel.gamepanel.handler;

import com.victor.panel.PanelController;

import java.util.Vector;

public abstract class HandlerSet {
    public enum InteractionMode {
        Drag, Select
    }
    
    private PanelController panelController;
    
    private Vector<BackgroundClickHandler> backgroundClickHandlers;
    private Vector<ClickHandler> clickHandlers;

    private Vector<BackgroundDragHandler> backgroundDragHandlers;
    private Vector<DragHandler> dragHandlers;
    
    private Vector<MouseWheelHandler> mouseWheelHandlers;
    
    //private BackgroundPopupMenu backgroundPopupMenu;
    
    public HandlerSet(PanelController panelController) {
        this.panelController = panelController;
        initializeHandlers();
    }
    
    public void reset() {
        initializeHandlers();
        addHandlers();
    }
    
    private void initializeHandlers() {

        backgroundClickHandlers = new Vector<BackgroundClickHandler>();
        clickHandlers = new Vector<ClickHandler>();

        backgroundDragHandlers = new Vector<BackgroundDragHandler>();
        dragHandlers = new Vector<DragHandler>();
        
        mouseWheelHandlers = new Vector<MouseWheelHandler>();
    }
    
    public abstract void addHandlers();
    
    
    public Iterable<BackgroundClickHandler> getBackgroundClickHandlers() {
        return backgroundClickHandlers;
    }
    
    public Iterable<ClickHandler> getClickHandlers() {
        return clickHandlers;
    }
        
    public Iterable<BackgroundDragHandler> getBackgroundDragHandlers() {
        return backgroundDragHandlers;
    }
    
    public Iterable<DragHandler> getDragHandlers() {
        return dragHandlers;
    }
    
    public Iterable<MouseWheelHandler> getMouseWheelHandlers() {
        return mouseWheelHandlers;
    }
    
    public void addBackgroundClickHandler(BackgroundClickHandler clickHandler) {
        backgroundClickHandlers.add(clickHandler);
    }
    
    public void addClickHandler(ClickHandler clickHandler) {
        clickHandlers.add(clickHandler);
    }
    
    
    public void removeBackgroundClickHandler(BackgroundClickHandler clickHandler) {
        backgroundClickHandlers.remove(clickHandler);
    }
    
    public void removeClickHandler(ClickHandler clickHandler) {
        clickHandlers.remove(clickHandler);
    }
    
    
    public void addBackgroundDragHandler(BackgroundDragHandler dragHandler) {
        backgroundDragHandlers.add(dragHandler);
    }
    
    public void addDragHandler(DragHandler dragHandler) {
        dragHandlers.add(dragHandler);
    }
        
    public void removeBackgroundDragHandler(BackgroundDragHandler dragHandler) {
        backgroundDragHandlers.remove(dragHandler);
    }
    
    public void removeDragHandler(DragHandler dragHandler) {
        dragHandlers.remove(dragHandler);
    }
    
    public void addMouseWheelHandler(MouseWheelHandler mouseWheelHandler) {
        mouseWheelHandlers.add(mouseWheelHandler);
    }
    
    public void removeMouseWheelHandler(MouseWheelHandler mouseWheelHandler) {
        mouseWheelHandlers.remove(mouseWheelHandler);
    }
}
