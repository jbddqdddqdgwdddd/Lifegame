package com.victor.panel;

import com.victor.Life;

import javax.swing.*;
import java.awt.*;

//游戏界面框架设计
public class GameFrame extends JFrame {
    private static final Dimension DEFAULT_WINDOW_SIZE = new Dimension(1200, 720);
    private static final Dimension MINIMUM_WINDOW_SIZE = new Dimension(300, 300);

    PanelController panelController;

    public GameFrame(PanelController panelController) {
        this.panelController = panelController;
        initFrame();
        setLayout(new BorderLayout(0, 0));
    }
    //初始化框架
    private void initFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setSize(DEFAULT_WINDOW_SIZE);
        setMinimumSize(MINIMUM_WINDOW_SIZE);
        setLocation(
                (Toolkit.getDefaultToolkit().getScreenSize().width - getWidth()) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - getHeight()) / 2);

    }

}
