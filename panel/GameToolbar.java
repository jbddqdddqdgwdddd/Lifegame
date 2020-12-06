package com.victor.panel;

import javax.swing.*;
import java.awt.*;
//游戏工具栏
public class GameToolbar extends JPanel {

    PanelController panelController;
    private JSlider zoomSlider;
    private JButton startPauseButton;
    private JSlider speedSlider;
    private Component horizontalStrut;
    private Component horizontalStrut_1;
    private JButton stepButton;
    private JButton resetButton;
    private JButton gifStopRecordingButton;


    public GameToolbar(PanelController panelController) {
        setMinimumSize(new Dimension(220, 0));

        this.panelController = panelController;
        buildPanel();
    }
    //创建面板
    public void buildPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        horizontalStrut = Box.createHorizontalStrut(20);
        add(horizontalStrut);

        JLabel lblZoom = new JLabel("环境大小");
        add(lblZoom);

        zoomSlider = new JSlider();
        zoomSlider.setPreferredSize(new Dimension(100, 29));
        add(zoomSlider);
        zoomSlider.setMinorTickSpacing(1);
        zoomSlider.setMinimum(-5);
        zoomSlider.setValue(-2);
        zoomSlider.setMaximum(5);

        JLabel speedLabel = new JLabel("速度");
        add(speedLabel);

        speedSlider = new JSlider();
        speedSlider.setPreferredSize(new Dimension(100, 29));
        speedSlider.setSnapToTicks(true);
        add(speedSlider);
        speedSlider.setMinimum(-5);
        speedSlider.setMaximum(4);
        speedSlider.setValue(-2);

        gifStopRecordingButton = new JButton("GIF停止录制.");
        gifStopRecordingButton.setVisible(false);
        add(gifStopRecordingButton);

        startPauseButton = new JButton("开始");
        add(startPauseButton);
        startPauseButton.setMaximumSize(new Dimension(200, 23));
        startPauseButton.setPreferredSize(new Dimension(80, 29));

        stepButton = new JButton("下一步");
        stepButton.setPreferredSize(new Dimension(80, 29));
        add(stepButton);

        resetButton = new JButton("重置");
        resetButton.setPreferredSize(new Dimension(80, 29));
        add(resetButton);

        horizontalStrut_1 = Box.createHorizontalStrut(20);
        add(horizontalStrut_1);

    }

    public int getInt(String s) {
        return panelController.getGameController().getSettings().getInt(s);
    }
    //获取游戏地图slider
    public JSlider getZoomSlider() {
        return zoomSlider;
    }
    //获取游戏开始和停止按钮
    public JButton getStartPauseButton() {
        return startPauseButton;
    }
    //获取游戏速度设置
    public JSlider getSpeedSlider() {
        return speedSlider;
    }
    //获取游戏步进设置
    public JButton getStepButton() {
        return stepButton;
    }
    //获取游戏重置设置
    public JButton getResetButton() {
        return resetButton;
    }
    //获取GIF录制设置
    public JButton getGifStopRecordingButton() {
        return gifStopRecordingButton;
    }
}
