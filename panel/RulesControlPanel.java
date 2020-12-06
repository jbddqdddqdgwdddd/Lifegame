package com.victor.panel;

import javax.swing.*;
import java.awt.*;


//游戏规则设置图形界面设置
public class RulesControlPanel extends JPanel {

    PanelController panelController;
    private JRadioButton rdbtnFriendly;
    private JRadioButton rdbtnCompetitive1;
    private JLabel lblSeedType;
    private JComboBox seedTypeComboBox;
    private JRadioButton rdbtnCompetitive2;

    private JSpinner maxLifespanSpinner;
    private JSpinner childOneParentAgeSpinner;
    private JSpinner childTwoParentAgeSpinner;
    private JSpinner childThreeParentAgeSpinner;
    private JLabel lblSproutMode;
    private JPanel panel;
    private JRadioButton rdbtnFunctional;
    private JRadioButton rdbtnVisual;
    private JLabel lblMutationRate;
    private JSpinner mutationRateSpinner;
    private JLabel lblTargetAge;
    private Component verticalStrut;
    private JSpinner targetAgeSpinner;
    private Component verticalStrut_1;

    public RulesControlPanel(PanelController panelController) {
        setMinimumSize(new Dimension(220, 0));
        setPreferredSize(new Dimension(260, 600));

        this.panelController = panelController;
        buildPanel();
    }
    //创建面板
    public void buildPanel() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {10, 100, 60};
        gridBagLayout.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 28, 28, 28, 28, 28, 28, 28, 28, 0, 30, 0, 0, 0, 30};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        setLayout(gridBagLayout);

        JPanel lifeModePanel = new JPanel();
        ButtonGroup lifeModeButtonGroup = new ButtonGroup();
        
        verticalStrut_1 = Box.createVerticalStrut(20);
        verticalStrut_1.setPreferredSize(new Dimension(0, 10));
        GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
        gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut_1.gridx = 0;
        gbc_verticalStrut_1.gridy = 2;
        add(verticalStrut_1, gbc_verticalStrut_1);

        lblSeedType = new JLabel("种子模式");
        lblSeedType.setMaximumSize(new Dimension(200, 16));
        lblSeedType.setMinimumSize(new Dimension(200, 16));
        lblSeedType.setPreferredSize(new Dimension(200, 16));
        GridBagConstraints gbc_lblSeedType = new GridBagConstraints();
        gbc_lblSeedType.anchor = GridBagConstraints.WEST;
        gbc_lblSeedType.insets = new Insets(0, 0, 5, 5);
        gbc_lblSeedType.gridx = 1;
        gbc_lblSeedType.gridy = 3;
        add(lblSeedType, gbc_lblSeedType);

        seedTypeComboBox = new JComboBox();
        seedTypeComboBox.setPreferredSize(new Dimension(200, 27));
        seedTypeComboBox.setMinimumSize(new Dimension(200, 27));
        seedTypeComboBox.setMaximumSize(new Dimension(200, 32767));
        GridBagConstraints gbc_seedTypeComboBox = new GridBagConstraints();
        gbc_seedTypeComboBox.anchor = GridBagConstraints.EAST;
        gbc_seedTypeComboBox.gridwidth = 2;
        gbc_seedTypeComboBox.insets = new Insets(0, 0, 5, 0);
        gbc_seedTypeComboBox.gridx = 1;
        gbc_seedTypeComboBox.gridy = 4;
        add(seedTypeComboBox, gbc_seedTypeComboBox);


        JLabel lblLifeMode = new JLabel("碰撞模式");
        GridBagConstraints gbc_lblLifeMode = new GridBagConstraints();
        gbc_lblLifeMode.gridwidth = 2;
        gbc_lblLifeMode.anchor = GridBagConstraints.WEST;
        gbc_lblLifeMode.insets = new Insets(0, 0, 5, 0);
        gbc_lblLifeMode.gridx = 1;
        gbc_lblLifeMode.gridy = 5;
        add(lblLifeMode, gbc_lblLifeMode);
        GridBagConstraints gbc_lifeModePanel = new GridBagConstraints();
        gbc_lifeModePanel.gridwidth = 2;
        gbc_lifeModePanel.anchor = GridBagConstraints.NORTH;
        gbc_lifeModePanel.insets = new Insets(0, 0, 5, 0);
        gbc_lifeModePanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lifeModePanel.gridx = 1;
        gbc_lifeModePanel.gridy = 6;
        add(lifeModePanel, gbc_lifeModePanel);
        GridBagLayout gbl_lifeModePanel = new GridBagLayout();
        gbl_lifeModePanel.columnWidths = new int[]{40};
        gbl_lifeModePanel.rowHeights = new int[]{23, 0, 0};
        gbl_lifeModePanel.columnWeights = new double[]{0.0};
        gbl_lifeModePanel.rowWeights = new double[]{0.0, 0.0, 0.0};
        lifeModePanel.setLayout(gbl_lifeModePanel);

        rdbtnFriendly = new JRadioButton("基本模式");
        rdbtnFriendly.setToolTipText("<html>生命之间的碰撞独立于<br>生命的体积.</html>");
        rdbtnFriendly.setAlignmentX(Component.RIGHT_ALIGNMENT);
        GridBagConstraints gbc_rdbtnFriendly = new GridBagConstraints();
        gbc_rdbtnFriendly.anchor = GridBagConstraints.WEST;
        gbc_rdbtnFriendly.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnFriendly.gridx = 0;
        gbc_rdbtnFriendly.gridy = 0;
        lifeModePanel.add(rdbtnFriendly, gbc_rdbtnFriendly);
        lifeModeButtonGroup.add(rdbtnFriendly);

        rdbtnCompetitive1 = new JRadioButton("狂野模式");
        rdbtnCompetitive1.setToolTipText("<html>更大规模的生命组会赢得碰撞.</html>");
        rdbtnCompetitive1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        GridBagConstraints gbc_rdbtnCompetitive = new GridBagConstraints();
        gbc_rdbtnCompetitive.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnCompetitive.anchor = GridBagConstraints.WEST;
        gbc_rdbtnCompetitive.gridx = 0;
        gbc_rdbtnCompetitive.gridy = 1;
        lifeModePanel.add(rdbtnCompetitive1, gbc_rdbtnCompetitive);
        lifeModeButtonGroup.add(rdbtnCompetitive1);

        rdbtnCompetitive2 = new JRadioButton("狂野模式2");
        rdbtnCompetitive2.setToolTipText("<html>生命根据体积决定胜负<br>没有繁衍分支的生命命组成<br>只有一个孩子的父母.</html>");
        rdbtnCompetitive2.setAlignmentX(1.0f);
        GridBagConstraints gbc_radioButton = new GridBagConstraints();
        gbc_radioButton.anchor = GridBagConstraints.WEST;
        gbc_radioButton.gridx = 0;
        gbc_radioButton.gridy = 2;
        lifeModePanel.add(rdbtnCompetitive2, gbc_radioButton);
        lifeModeButtonGroup.add(rdbtnCompetitive2);


        childOneParentAgeSpinner = new JSpinner();
        childOneParentAgeSpinner.setPreferredSize(new Dimension(50, 20));

        JLabel lblMaxLifespan = new JLabel("最大寿命");
        GridBagConstraints gbc_lblMaxLifespan = new GridBagConstraints();
        gbc_lblMaxLifespan.anchor = GridBagConstraints.WEST;
        gbc_lblMaxLifespan.insets = new Insets(0, 0, 5, 5);
        gbc_lblMaxLifespan.gridx = 1;
        gbc_lblMaxLifespan.gridy = 8;
        add(lblMaxLifespan, gbc_lblMaxLifespan);

        maxLifespanSpinner = new JSpinner();
        maxLifespanSpinner.setPreferredSize(new Dimension(50, 20));
        GridBagConstraints gbc_maxLifespanSpinner = new GridBagConstraints();
        gbc_maxLifespanSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxLifespanSpinner.anchor = GridBagConstraints.NORTH;
        gbc_maxLifespanSpinner.insets = new Insets(0, 0, 5, 0);
        gbc_maxLifespanSpinner.gridx = 2;
        gbc_maxLifespanSpinner.gridy = 8;
        add(maxLifespanSpinner, gbc_maxLifespanSpinner);

        lblTargetAge = new JLabel("竞争目标寿命");
        GridBagConstraints gbc_lblTargetAge = new GridBagConstraints();
        gbc_lblTargetAge.anchor = GridBagConstraints.WEST;
        gbc_lblTargetAge.insets = new Insets(0, 0, 5, 5);
        gbc_lblTargetAge.gridx = 1;
        gbc_lblTargetAge.gridy = 9;
        add(lblTargetAge, gbc_lblTargetAge);

        targetAgeSpinner = new JSpinner();
        targetAgeSpinner.setToolTipText("<html>最小目标年龄<br>在每一代都会变化.<br>但是减少变异几率.</html>");
        GridBagConstraints gbc_targetAgeSpinner = new GridBagConstraints();
        gbc_targetAgeSpinner.anchor = GridBagConstraints.NORTH;
        gbc_targetAgeSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_targetAgeSpinner.insets = new Insets(0, 0, 5, 0);
        gbc_targetAgeSpinner.gridx = 2;
        gbc_targetAgeSpinner.gridy = 9;
        add(targetAgeSpinner, gbc_targetAgeSpinner);

        verticalStrut = Box.createVerticalStrut(20);
        GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
        gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
        gbc_verticalStrut.gridx = 1;
        gbc_verticalStrut.gridy = 10;
        add(verticalStrut, gbc_verticalStrut);

        JLabel childOneEnergyLabel = new JLabel("生一胎的最小年龄");
        GridBagConstraints gbc_childOneEnergyLabel = new GridBagConstraints();
        gbc_childOneEnergyLabel.anchor = GridBagConstraints.WEST;
        gbc_childOneEnergyLabel.insets = new Insets(0, 0, 5, 5);
        gbc_childOneEnergyLabel.gridx = 1;
        gbc_childOneEnergyLabel.gridy = 11;
        add(childOneEnergyLabel, gbc_childOneEnergyLabel);
        GridBagConstraints gbc_childOneEnergySpinner = new GridBagConstraints();
        gbc_childOneEnergySpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_childOneEnergySpinner.anchor = GridBagConstraints.NORTH;
        gbc_childOneEnergySpinner.insets = new Insets(0, 0, 5, 0);
        gbc_childOneEnergySpinner.gridx = 2;
        gbc_childOneEnergySpinner.gridy = 11;
        add(childOneParentAgeSpinner, gbc_childOneEnergySpinner);

        childTwoParentAgeSpinner = new JSpinner();
        childTwoParentAgeSpinner.setPreferredSize(new Dimension(50, 20));

        JLabel childTwoEnergyLabel = new JLabel("生二胎的最小年龄");
        GridBagConstraints gbc_childTwoEnergyLabel = new GridBagConstraints();
        gbc_childTwoEnergyLabel.anchor = GridBagConstraints.WEST;
        gbc_childTwoEnergyLabel.insets = new Insets(0, 0, 5, 5);
        gbc_childTwoEnergyLabel.gridx = 1;
        gbc_childTwoEnergyLabel.gridy = 12;
        add(childTwoEnergyLabel, gbc_childTwoEnergyLabel);
        GridBagConstraints gbc_childTwoEnergySpinner = new GridBagConstraints();
        gbc_childTwoEnergySpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_childTwoEnergySpinner.anchor = GridBagConstraints.NORTH;
        gbc_childTwoEnergySpinner.insets = new Insets(0, 0, 5, 0);
        gbc_childTwoEnergySpinner.gridx = 2;
        gbc_childTwoEnergySpinner.gridy = 12;
        add(childTwoParentAgeSpinner, gbc_childTwoEnergySpinner);

        childThreeParentAgeSpinner = new JSpinner();
        childThreeParentAgeSpinner.setPreferredSize(new Dimension(50, 20));

        JLabel childThreeEnergyLabel = new JLabel("生三或更多胎的最小年龄");
        GridBagConstraints gbc_childThreeEnergyLabel = new GridBagConstraints();
        gbc_childThreeEnergyLabel.anchor = GridBagConstraints.WEST;
        gbc_childThreeEnergyLabel.insets = new Insets(0, 0, 5, 5);
        gbc_childThreeEnergyLabel.gridx = 1;
        gbc_childThreeEnergyLabel.gridy = 13;
        add(childThreeEnergyLabel, gbc_childThreeEnergyLabel);
        GridBagConstraints gbc_childThreeEnergySpinner = new GridBagConstraints();
        gbc_childThreeEnergySpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_childThreeEnergySpinner.insets = new Insets(0, 0, 5, 0);
        gbc_childThreeEnergySpinner.anchor = GridBagConstraints.NORTH;
        gbc_childThreeEnergySpinner.gridx = 2;
        gbc_childThreeEnergySpinner.gridy = 13;
        add(childThreeParentAgeSpinner, gbc_childThreeEnergySpinner);

        lblMutationRate = new JLabel("变异几率 (0-10)");
        GridBagConstraints gbc_lblMutationRate = new GridBagConstraints();
        gbc_lblMutationRate.anchor = GridBagConstraints.WEST;
        gbc_lblMutationRate.insets = new Insets(0, 0, 5, 5);
        gbc_lblMutationRate.gridx = 1;
        gbc_lblMutationRate.gridy = 15;
        add(lblMutationRate, gbc_lblMutationRate);

        mutationRateSpinner = new JSpinner();
        mutationRateSpinner.setModel(new SpinnerNumberModel(5, 0, 10, 1));
        GridBagConstraints gbc_mutationRateSpinner = new GridBagConstraints();
        gbc_mutationRateSpinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_mutationRateSpinner.insets = new Insets(0, 0, 5, 0);
        gbc_mutationRateSpinner.gridx = 2;
        gbc_mutationRateSpinner.gridy = 15;
        add(mutationRateSpinner, gbc_mutationRateSpinner);


        lblSproutMode = new JLabel("种子成长模式");
        GridBagConstraints gbc_lblSproutMode = new GridBagConstraints();
        gbc_lblSproutMode.anchor = GridBagConstraints.WEST;
        gbc_lblSproutMode.insets = new Insets(0, 0, 5, 5);
        gbc_lblSproutMode.gridx = 1;
        gbc_lblSproutMode.gridy = 17;
        add(lblSproutMode, gbc_lblSproutMode);

        panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.EAST;
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.fill = GridBagConstraints.VERTICAL;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 18;
        add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{30, 40, 40, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        ButtonGroup sproutModeButtonGroup = new ButtonGroup();
        rdbtnFunctional = new JRadioButton("程式化");
        sproutModeButtonGroup.add(rdbtnFunctional);
        rdbtnFunctional.setToolTipText("<html>种子会直接被之前已经成长的种子替代显示.<br>取得更有效的繁殖.</html>");
        rdbtnFunctional.setSelected(true);
        rdbtnFunctional.setAlignmentX(1.0f);
        GridBagConstraints gbc_radioButtonFunctional = new GridBagConstraints();
        gbc_radioButton.anchor = GridBagConstraints.EAST;
        gbc_radioButton.insets = new Insets(0, 0, 0, 5);
        gbc_radioButton.gridx = 1;
        gbc_radioButton.gridy = 0;
        panel.add(rdbtnFunctional, gbc_radioButtonFunctional);

        rdbtnVisual = new JRadioButton("可视化");
        sproutModeButtonGroup.add(rdbtnVisual);
        rdbtnVisual.setToolTipText("<html>种子被设置为可见<br>但是会加入更多其成长的步骤.</html>");
        GridBagConstraints gbc_rdbtnVisual = new GridBagConstraints();
        gbc_rdbtnVisual.anchor = GridBagConstraints.EAST;
        gbc_rdbtnVisual.gridx = 2;
        gbc_rdbtnVisual.gridy = 0;
        panel.add(rdbtnVisual, gbc_rdbtnVisual);

    }

    //和组件事件触发创建连接
    public JRadioButton getRdbtnFriendly() {
        return rdbtnFriendly;
    }
    public JRadioButton getRdbtnCompetitive1() {
        return rdbtnCompetitive1;
    }
    public JRadioButton getRdbtnCompetitive2() {
        return rdbtnCompetitive2;
    }
    public JComboBox getSeedTypeComboBox() {
        return seedTypeComboBox;
    }

    public JSpinner getMaxLifespanSpinner() {
        return maxLifespanSpinner;
    }

    public JSpinner getChildOneParentAgeSpinner() {
        return childOneParentAgeSpinner;
    }
    public JSpinner getChildTwoParentAgeSpinner() {
        return childTwoParentAgeSpinner;
    }
    public JSpinner getChildThreeParentAgeSpinner() {
        return childThreeParentAgeSpinner;
    }

    public JRadioButton getRdbtnVisual() {
        return rdbtnVisual;       
    }
    public JRadioButton getRdbtnFunctional() {
        return rdbtnFunctional;
    }

    public JSpinner getMutationRateSpinner() {
        return mutationRateSpinner;
    }
    public JSpinner getTargetAgeSpinner() {
        return targetAgeSpinner;
    }
}
