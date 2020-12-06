package com.victor.panel;

import javax.swing.*;
import java.awt.*;
//面板首页图形界面
public class DisplayControlPanel extends JPanel {

    PanelController panelController;
    private JPanel panel_2;
    private JCheckBox chckbxCellLayer;
    private JCheckBox chckbxGenomeLayer;
    private JCheckBox chckbxOrgHeadLayer;
    private JCheckBox chckbxOrgTailLayer;
    private JLabel lblDrawLayers;
    private JCheckBox chckbxOutlineSeeds;
    private JLabel lblColors;
    private JPanel panel;
    private JRadioButton rdbtnBackgroundWhite;
    private JRadioButton rdbtnBackgroundBlack;
    private final ButtonGroup buttonGroupBackground = new ButtonGroup();
    private Component verticalStrut;
    private JSpinner spinnerTailLength;
    private Box horizontalBox_1;
    private JCheckBox chckbxAutoSplitColors;

    private JSpinner boardWidthSpinner;
    private JSpinner boardHeightSpinner;
    private JCheckBox autoSizeGridCheckbox;
    private JButton clipGridToViewButton;
    private JLabel lblImage;
    private JSpinner imageWidthSpinner;
    private JSpinner imageHeightSpinner;
    private Component verticalStrut_2;
    private Component horizontalStrut;
    private JLabel labelBackground;
    private JRadioButton rdbtnMultiColorMode;
    private JRadioButton rdbtnTriColorMode;
    private final ButtonGroup buttonGroupColorMode = new ButtonGroup();
    private JPanel panel_1;
    private JSpinner spinnerColorScheme;
    private Component horizontalStrut_1;


    public DisplayControlPanel(PanelController panelController) {
        setMinimumSize(new Dimension(220, 0));
        setPreferredSize(new Dimension(280, 600));

        this.panelController = panelController;
        buildPanel();
    }
    //创建面板
    public void buildPanel() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {10, 10, 30, 100};
        gridBagLayout.rowHeights = new int[]{20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        setLayout(gridBagLayout);

        lblDrawLayers = new JLabel("图层");
        GridBagConstraints gbc_lblDrawLayers = new GridBagConstraints();
        gbc_lblDrawLayers.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblDrawLayers.insets = new Insets(0, 0, 5, 5);
        gbc_lblDrawLayers.gridx = 2;
        gbc_lblDrawLayers.gridy = 1;
        add(lblDrawLayers, gbc_lblDrawLayers);

        horizontalStrut = Box.createHorizontalStrut(20);
        horizontalStrut.setPreferredSize(new Dimension(5, 0));
        GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
        gbc_horizontalStrut.insets = new Insets(0, 0, 5, 0);
        gbc_horizontalStrut.gridx = 5;
        gbc_horizontalStrut.gridy = 1;
        add(horizontalStrut, gbc_horizontalStrut);

        panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridwidth = 3;
        gbc_panel_2.insets = new Insets(0, 30, 5, 5);
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 2;
        gbc_panel_2.gridy = 2;
        add(panel_2, gbc_panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

        chckbxCellLayer = new JCheckBox("显示细胞");
        chckbxCellLayer.setSelected(true);
        panel_2.add(chckbxCellLayer);

        chckbxGenomeLayer = new JCheckBox("显示生命组");
        chckbxGenomeLayer.setSelected(true);

        chckbxOrgHeadLayer = new JCheckBox("显示生命头部");
        chckbxOrgHeadLayer.setSelected(true);

        horizontalBox_1 = Box.createHorizontalBox();
        horizontalBox_1.setAlignmentX(Component.LEFT_ALIGNMENT);

        chckbxOrgTailLayer = new JCheckBox("显示尾层，长度");
        chckbxOrgTailLayer.setSelected(true);

        spinnerTailLength = new JSpinner();
        spinnerTailLength.setModel(new SpinnerNumberModel(9, 1, 20, 1));
        horizontalBox_1.add(spinnerTailLength);

        chckbxOutlineSeeds = new JCheckBox("显示细胞种子生长过程");
        panel_2.add(chckbxOutlineSeeds);

        verticalStrut = Box.createVerticalStrut(20);
        verticalStrut.setPreferredSize(new Dimension(0, 10));
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 0;
        gbc_verticalStrut.gridy = 3;
        add(verticalStrut, gbc_verticalStrut);

        lblColors = new JLabel("颜色设置");
        GridBagConstraints gbc_lblColors = new GridBagConstraints();
        gbc_lblColors.gridwidth = 2;
        gbc_lblColors.anchor = GridBagConstraints.NORTHWEST;
        gbc_lblColors.insets = new Insets(0, 0, 5, 5);
        gbc_lblColors.gridx = 2;
        gbc_lblColors.gridy = 4;
        add(lblColors, gbc_lblColors);

        panel = new JPanel();
        panel.setMaximumSize(new Dimension(32767, 50));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTHWEST;
        gbc_panel.gridwidth = 4;
        gbc_panel.insets = new Insets(0, 30, 5, 0);
        gbc_panel.gridx = 2;
        gbc_panel.gridy = 5;
        add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        labelBackground = new JLabel("背景颜色");
        panel.add(labelBackground);

        rdbtnBackgroundBlack = new JRadioButton("黑色");
        rdbtnBackgroundBlack.setMargin(new Insets(1, 5, 0, 1));
        buttonGroupBackground.add(rdbtnBackgroundBlack);
        rdbtnBackgroundBlack.setSelected(true);
        panel.add(rdbtnBackgroundBlack);

        rdbtnBackgroundWhite = new JRadioButton("白色");
        buttonGroupBackground.add(rdbtnBackgroundWhite);
        panel.add(rdbtnBackgroundWhite);

        rdbtnMultiColorMode = new JRadioButton("多配色模式");
        rdbtnMultiColorMode.setSelected(true);
        buttonGroupColorMode.add(rdbtnMultiColorMode);
        GridBagConstraints gbc_rdbtnMultiColorMode = new GridBagConstraints();
        gbc_rdbtnMultiColorMode.anchor = GridBagConstraints.NORTHWEST;
        gbc_rdbtnMultiColorMode.gridwidth = 2;
        gbc_rdbtnMultiColorMode.insets = new Insets(0, 30, 5, 5);
        gbc_rdbtnMultiColorMode.gridx = 2;
        gbc_rdbtnMultiColorMode.gridy = 6;
        add(rdbtnMultiColorMode, gbc_rdbtnMultiColorMode);

        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.gridwidth = 3;
        gbc_panel_1.insets = new Insets(0, 60, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 7;
        add(panel_1, gbc_panel_1);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

        JLabel lblColorScheme = new JLabel("色彩方案");
        panel_1.add(lblColorScheme);

        horizontalStrut_1 = Box.createHorizontalStrut(20);
        panel_1.add(horizontalStrut_1);

        spinnerColorScheme = new JSpinner();
        spinnerColorScheme.setMaximumSize(new Dimension(50, 32767));
        SpinnerModel primaryHueModel = new SpinnerNumberModel(1, 1, 12, 1);
        spinnerColorScheme.setModel(primaryHueModel);
        panel_1.add(spinnerColorScheme);

        rdbtnTriColorMode = new JRadioButton("三色模式");
        buttonGroupColorMode.add(rdbtnTriColorMode);
        GridBagConstraints gbc_rdbtnTriColorMode = new GridBagConstraints();
        gbc_rdbtnTriColorMode.anchor = GridBagConstraints.NORTHWEST;
        gbc_rdbtnTriColorMode.gridwidth = 2;
        gbc_rdbtnTriColorMode.insets = new Insets(0, 30, 5, 5);
        gbc_rdbtnTriColorMode.gridx = 2;
        gbc_rdbtnTriColorMode.gridy = 8;
        add(rdbtnTriColorMode, gbc_rdbtnTriColorMode);

        chckbxAutoSplitColors = new JCheckBox("自动配色");
        GridBagConstraints gbc_chckbxAutoSplitColors = new GridBagConstraints();
        gbc_chckbxAutoSplitColors.anchor = GridBagConstraints.NORTHWEST;
        gbc_chckbxAutoSplitColors.gridwidth = 3;
        gbc_chckbxAutoSplitColors.insets = new Insets(0, 60, 5, 5);
        gbc_chckbxAutoSplitColors.gridx = 2;
        gbc_chckbxAutoSplitColors.gridy = 9;
        add(chckbxAutoSplitColors, gbc_chckbxAutoSplitColors);
        chckbxAutoSplitColors.setToolTipText("<html>当生命组颜色过多时<br>将颜色自动分开.</html>");
        chckbxAutoSplitColors.setSelected(true);

        verticalStrut_2 = Box.createVerticalStrut(20);
        verticalStrut_2.setPreferredSize(new Dimension(0, 10));
        GridBagConstraints gbc_verticalStrut_2 = new GridBagConstraints();
        gbc_verticalStrut_2.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_2.gridx = 2;
        gbc_verticalStrut_2.gridy = 10;
        add(verticalStrut_2, gbc_verticalStrut_2);


        JPanel sizePanel = new JPanel();
        GridBagConstraints gbc_sizePanel = new GridBagConstraints();
        gbc_sizePanel.insets = new Insets(0, 0, 5, 5);
        gbc_sizePanel.gridwidth = 3;
        gbc_sizePanel.fill = GridBagConstraints.BOTH;
        gbc_sizePanel.gridx = 2;
        gbc_sizePanel.gridy = 11;
        add(sizePanel, gbc_sizePanel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{30, 40, 40};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        sizePanel.setLayout(gbl_panel);

        JLabel lblGrid = new JLabel("界面宽 ：高:");
        GridBagConstraints gbc_lblGrid = new GridBagConstraints();
        gbc_lblGrid.anchor = GridBagConstraints.WEST;
        gbc_lblGrid.insets = new Insets(0, 0, 5, 5);
        gbc_lblGrid.gridx = 0;
        gbc_lblGrid.gridy = 0;
        sizePanel.add(lblGrid, gbc_lblGrid);

        boardWidthSpinner = new JSpinner();
        boardWidthSpinner.setPreferredSize(new Dimension(120, 26));
        GridBagConstraints gbc_boardWidthSpinner = new GridBagConstraints();
        gbc_boardWidthSpinner.insets = new Insets(0, 0, 5, 5);
        gbc_boardWidthSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_boardWidthSpinner.gridx = 1;
        gbc_boardWidthSpinner.gridy = 0;
        sizePanel.add(boardWidthSpinner, gbc_boardWidthSpinner);

        boardHeightSpinner = new JSpinner();
        boardHeightSpinner.setPreferredSize(new Dimension(120, 26));
        GridBagConstraints gbc_boardHeightSpinner = new GridBagConstraints();
        gbc_boardHeightSpinner.insets = new Insets(0, 0, 5, 0);
        gbc_boardHeightSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_boardHeightSpinner.gridx = 2;
        gbc_boardHeightSpinner.gridy = 0;
        sizePanel.add(boardHeightSpinner, gbc_boardHeightSpinner);

        lblImage = new JLabel("图像 宽 ： 高:");
        GridBagConstraints gbc_lblImage = new GridBagConstraints();
        gbc_lblImage.anchor = GridBagConstraints.WEST;
        gbc_lblImage.insets = new Insets(0, 0, 5, 5);
        gbc_lblImage.gridx = 0;
        gbc_lblImage.gridy = 2;
        sizePanel.add(lblImage, gbc_lblImage);

        imageWidthSpinner = new JSpinner();
        GridBagConstraints gbc_imageWidthSpinner = new GridBagConstraints();
        gbc_imageWidthSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_imageWidthSpinner.insets = new Insets(0, 0, 5, 5);
        gbc_imageWidthSpinner.gridx = 1;
        gbc_imageWidthSpinner.gridy = 2;
        sizePanel.add(imageWidthSpinner, gbc_imageWidthSpinner);
        imageWidthSpinner.setPreferredSize(new Dimension(100, 26));

        imageHeightSpinner = new JSpinner();
        GridBagConstraints gbc_imageHeightSpinner = new GridBagConstraints();
        gbc_imageHeightSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_imageHeightSpinner.insets = new Insets(0, 0, 5, 0);
        gbc_imageHeightSpinner.gridx = 2;
        gbc_imageHeightSpinner.gridy = 2;
        sizePanel.add(imageHeightSpinner, gbc_imageHeightSpinner);
        imageHeightSpinner.setPreferredSize(new Dimension(100, 26));

        autoSizeGridCheckbox = new JCheckBox("自动尺寸调整");
        GridBagConstraints gbc_autoSizeGridCheckbox = new GridBagConstraints();
        gbc_autoSizeGridCheckbox.anchor = GridBagConstraints.WEST;
        gbc_autoSizeGridCheckbox.insets = new Insets(0, 0, 5, 5);
        gbc_autoSizeGridCheckbox.gridx = 0;
        gbc_autoSizeGridCheckbox.gridy = 4;
        sizePanel.add(autoSizeGridCheckbox, gbc_autoSizeGridCheckbox);
        autoSizeGridCheckbox.setSelected(true);

        clipGridToViewButton = new JButton("点击适配");
        GridBagConstraints gbc_clipGridToViewButton = new GridBagConstraints();
        gbc_clipGridToViewButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_clipGridToViewButton.gridwidth = 2;
        gbc_clipGridToViewButton.insets = new Insets(0, 0, 5, 0);
        gbc_clipGridToViewButton.gridx = 1;
        gbc_clipGridToViewButton.gridy = 4;
        sizePanel.add(clipGridToViewButton, gbc_clipGridToViewButton);
        clipGridToViewButton.setPreferredSize(new Dimension(120, 29));
        clipGridToViewButton.setMargin(new Insets(2, 2, 2, 2));

    }
    //连接面板按钮勾选框和监听事件
    public JCheckBox getChckbxCellLayer() {
        return chckbxCellLayer;
    }
    public JCheckBox getChckbxGenomeLayer() {
        return chckbxGenomeLayer;
    }
    public JCheckBox getChckbxOrgHeadLayer() {
        return chckbxOrgHeadLayer;
    }
    public JCheckBox getChckbxOrgTailLayer() {
        return chckbxOrgTailLayer;
    }
    public JCheckBox getChckbxOutlineSeeds() {
        return chckbxOutlineSeeds;
    }
    public JRadioButton getRdbtnBackgroundBlack() {
        return rdbtnBackgroundBlack;
    }
    public JRadioButton getRdbtnBackgroundWhite() {
        return rdbtnBackgroundWhite;
    }
    public JSpinner getSpinnerTailLength() {
        return spinnerTailLength;
    }
    public JCheckBox getChckbxAutoSplitColors() {
        return chckbxAutoSplitColors;
    }
    public JSpinner getBoardWidthSpinner() {
        return boardWidthSpinner;
    }
    public JSpinner getBoardHeightSpinner() {
        return boardHeightSpinner;
    }
    public JCheckBox getAutoSizeGridCheckbox() {
        return autoSizeGridCheckbox;
    }
    public JButton getClipGridToViewButton() {
        return clipGridToViewButton;
    }
    public JSpinner getImageWidthSpinner() {
        return imageWidthSpinner;
    }
    public JSpinner getImageHeightSpinner() {
        return imageHeightSpinner;
    }
    public JRadioButton getRdbtnMultiColorMode() {
        return rdbtnMultiColorMode;
    }
    public JRadioButton getRdbtnTriColorMode() {
        return rdbtnTriColorMode;
    }
    public JSpinner getSpinnerColorScheme() {
        return spinnerColorScheme;
    }
}
