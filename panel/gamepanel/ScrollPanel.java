package com.victor.panel.gamepanel;


import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.victor.panel.PanelController;

//滚动面板
@SuppressWarnings("serial")
public class ScrollPanel extends JPanel {
    private static final int SCROLL_UNIT_INCREMENT = 10;
    private static final int SCROLL_BLOCK_INCREMENT_SUBTRACT_FROM_EXTENT = 10;
    private static final int MIN_SCROLL_BLOCK_INCREMENT = 10;

    private PanelController panelController;
    private Vector<ViewportResizedListener> viewportResizedListeners;
    private Vector<ViewportMovedListener> viewportMovedListeners;
    private BoundedRangeModel hScrollModel;
    private BoundedRangeModel vScrollModel;
    private JScrollBar hScrollBar;
    private JScrollBar vScrollBar;
    private JPanel innerPanel;
    private boolean isUpdating = false;


    public ScrollPanel(PanelController panelController) {
        this.panelController = panelController;
        viewportResizedListeners = new Vector<ViewportResizedListener>();
        viewportMovedListeners = new Vector<ViewportMovedListener>();
        vScrollModel = new DefaultBoundedRangeModel();
        hScrollModel = new DefaultBoundedRangeModel();
        buildPanel();
        createListeners();
    }
    //建立新面板
    private void buildPanel() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints layoutCons = new GridBagConstraints();
        setLayout(layout);
        innerPanel = new JPanel() {
            public void paint(Graphics g) {
                boolean gotLock = false;
                try {
                    boolean success=false;
                    while(!success) {
                        gotLock = panelController.getInteractionLock().readLock().tryLock(100, TimeUnit.MILLISECONDS);
                        if (gotLock) {
                            success=true;
                            super.paint(g);
                            Graphics2D g2 = (Graphics2D)g;
                            panelController.getImageManager().paint(g2);
                        }
                    }
                }
                catch(Exception ex) {
                    ex.printStackTrace();
                }
                finally {
                    if (gotLock) {
                        panelController.getInteractionLock().readLock().unlock();
                    }
                }
            }
        };

        layoutCons.gridx = 0;
        layoutCons.gridy = 0;
        layoutCons.gridwidth = 100;
        layoutCons.gridheight = 100;
        layoutCons.fill = GridBagConstraints.BOTH;
        layoutCons.insets = new Insets(1,1,0,0);
        layoutCons.anchor = GridBagConstraints.NORTH;
        layoutCons.weightx = 120.0;
        layoutCons.weighty = 120.0;
        layout.setConstraints(innerPanel, layoutCons);
        add(innerPanel);

        vScrollBar = new JScrollBar(JScrollBar.VERTICAL);
        vScrollBar.setModel(vScrollModel);
        layoutCons.gridx = 100;
        layoutCons.gridy = 0;
        layoutCons.gridwidth = GridBagConstraints.REMAINDER;
        layoutCons.gridheight = 100;
        layoutCons.fill = GridBagConstraints.VERTICAL;
        layoutCons.insets = new Insets(1,0,0,1);
        layoutCons.anchor = GridBagConstraints.NORTH;
        layoutCons.weightx = 0.0;
        layoutCons.weighty = 120.0;
        layout.setConstraints(vScrollBar, layoutCons);
        add(vScrollBar);

        hScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
        hScrollBar.setModel(hScrollModel);
        layoutCons.gridx = 0;
        layoutCons.gridy = 100;
        layoutCons.gridwidth = 100;
        layoutCons.gridheight = GridBagConstraints.REMAINDER;
        layoutCons.fill = GridBagConstraints.HORIZONTAL;
        layoutCons.insets = new Insets(0,1,1,0);
        layoutCons.anchor = GridBagConstraints.NORTH;
        layoutCons.weightx = 120.0;
        layoutCons.weighty = 0.0;
        layout.setConstraints(hScrollBar, layoutCons);
        add(hScrollBar);

        JPanel cornerPanel = new JPanel();
        layoutCons.gridx = 100;
        layoutCons.gridy = 100;
        layoutCons.gridwidth = GridBagConstraints.REMAINDER;
        layoutCons.gridheight = GridBagConstraints.REMAINDER;
        layoutCons.fill = GridBagConstraints.BOTH;
        layoutCons.insets = new Insets(0,0,1,1);
        layoutCons.anchor = GridBagConstraints.NORTH;
        layoutCons.weightx = 0.0;
        layoutCons.weighty = 0.0;
        layout.setConstraints(cornerPanel, layoutCons);
        add(cornerPanel);
    }
    //获取可见空间大小
    public Dimension getViewportSize() {
        return new Dimension(innerPanel.getWidth(),innerPanel.getHeight());
    }
    //获取方框中的可见区域
    public Rectangle getViewportRectangle() {
        return new Rectangle(
                hScrollModel.getValue(),
                vScrollModel.getValue(),
                innerPanel.getWidth(),
                innerPanel.getHeight());
    }
    //添加鼠标监听器
    public void addMouseListener(MouseListener ml) {
        innerPanel.addMouseListener(ml);
    }
    public void addMouseMotionListener(MouseMotionListener mml) {
        innerPanel.addMouseMotionListener(mml);
    }
    //添加鼠标滚轮监听器
    public void addMouseWheelListener(MouseWheelListener mwl) {
        innerPanel.addMouseWheelListener(mwl);
    }

    //移除监听器
    public void removeMouseListener(MouseListener ml) {
        innerPanel.removeMouseListener(ml);
    }

    public void removeMouseMotionListener(MouseMotionListener mml) {
        innerPanel.removeMouseMotionListener(mml);
    }

    public void removeMouseWheelListener(MouseWheelListener mwl) {
        innerPanel.removeMouseWheelListener(mwl);
    }
    //开启监听器
    public void enableMouseListeners() {

        addMouseListener(panelController.getInteractionHandler());
        addMouseMotionListener(panelController.getInteractionHandler());
        addMouseWheelListener(panelController.getInteractionHandler());

    }

    public void disableMouseListeners() {

        removeMouseListener(panelController.getInteractionHandler());
        removeMouseMotionListener(panelController.getInteractionHandler());
        removeMouseWheelListener(panelController.getInteractionHandler());

    }
    //更新监听状态
    protected void update(final int minX, final int maxX, final int xValue, final int xExtent,
                          final int minY, final int maxY, final int yValue, final int yExtent) {
        Runnable updater = new Runnable() {
            public void run() {
                isUpdating = true;
                hScrollModel.setRangeProperties(xValue, xExtent, minX, maxX, false);
                vScrollModel.setRangeProperties(yValue, yExtent, minY, maxY, false);
                hScrollBar.setBlockIncrement(Math.max(MIN_SCROLL_BLOCK_INCREMENT, xExtent-SCROLL_BLOCK_INCREMENT_SUBTRACT_FROM_EXTENT));
                vScrollBar.setBlockIncrement(Math.max(MIN_SCROLL_BLOCK_INCREMENT, yExtent-SCROLL_BLOCK_INCREMENT_SUBTRACT_FROM_EXTENT));
                hScrollBar.setUnitIncrement(SCROLL_UNIT_INCREMENT);
                vScrollBar.setUnitIncrement(SCROLL_UNIT_INCREMENT);
                isUpdating = false;
            }
        };

        SwingUtilities.invokeLater(updater);

    }
    //更新定位

    protected void update(final int xValue, final int yValue) {
        Runnable updater = new Runnable() {
            public void run() {
                isUpdating = true;
                hScrollModel.setValue(xValue);
                vScrollModel.setValue(yValue);
                isUpdating = false;
            }
        };

        SwingUtilities.invokeLater(updater);
    }
    //创建监听器组
    private void createListeners() {
        innerPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                fireViewportResized(innerPanel.getWidth(), innerPanel.getHeight());
            }
        });
        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                if ( !isUpdating ) {
                    fireViewportMoved(hScrollModel.getValue(),vScrollModel.getValue());
                }
            }
        };
        vScrollModel.addChangeListener(changeListener);
        hScrollModel.addChangeListener(changeListener);
    }
    //重新设置面板大小
    private synchronized void fireViewportResized(int viewportWidth, int viewportHeight) {
        for ( ViewportResizedListener gvrl : viewportResizedListeners ) {
            gvrl.viewportResized(viewportWidth, viewportHeight);
        }
    }

    public synchronized void addViewportResizedListener(ViewportResizedListener gvrl) {
        viewportResizedListeners.add(gvrl);
    }

    public void removeViewportResizedListener(ViewportResizedListener gvrl) {
        viewportResizedListeners.remove(gvrl);
    }

    public interface ViewportResizedListener {
        public void viewportResized(int viewportWidth, int viewportHeight);
    }

    private void fireViewportMoved(int viewportX, int viewportY) {
        for ( ViewportMovedListener gvml : viewportMovedListeners ) {
            gvml.viewportMoved(viewportX, viewportY);
        }
    }
    //添加视图监听
    public void addViewportMovedListener(ViewportMovedListener gvml) {
        viewportMovedListeners.add(gvml);
    }

    public void removeViewportMovedListener(ViewportMovedListener gvml) {
        viewportMovedListeners.remove(gvml);
    }

    public interface ViewportMovedListener {
        public void viewportMoved(int viewportX, int viewportY);
    }
}