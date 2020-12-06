package com.victor.action;

import com.victor.Settings;
import com.victor.io.GegrpIo;
import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

//加载游戏数据（已保存的文件）事件
@SuppressWarnings("serial")
public class LoadGegrpAction extends AbstractAction {

    protected PanelController controller;
    private JFileChooser chooser = null;
    static public int colorKind = 0;

    public LoadGegrpAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
    }

    public LoadGegrpAction(PanelController controller) {
        this(controller, "加载游戏");
    }

    public void initChooser() {
        File cd = null;
        if (chooser!=null) {
            cd = chooser.getCurrentDirectory();
        }
        chooser = new JFileChooser();
        if (cd==null) {
            chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        }
        else {
            chooser.setCurrentDirectory(cd);
        }
        chooser.setAcceptAllFileFilterUsed(false);

        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".txt")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "txt files (*.txt)";
            }
        });
    }
    //执行游戏加载
    public void actionPerformed(ActionEvent e) {
        initChooser();
        controller.setPlayGame(false);
        int returnVal = chooser.showOpenDialog(controller.getGameFrame());
        File loadFile;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            loadFile = chooser.getSelectedFile();
            String fileName = loadFile.getName();
            if (fileName.indexOf(".") < 0) {
                try {
                    String filePath = loadFile.getPath();
                    loadFile = new File(filePath + ".txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                GegrpIo.loadGegrp(loadFile, controller.getGameModel(), colorKind);
                if (colorKind>=1) {
                    //目前只是一种骇客。 来源：https://blog.csdn.net/guixiang155cm/article/details/4247922
                    //当用户重置时，colorKind设置为0，并在加载后递增。
                    //当colorKind为1或更大时，表示用户加载了第二或第三个生命组，因此
                    //我们切换到三色“ splitColor”模式。
                    controller.getDisplayControlPanel().getChckbxAutoSplitColors().setSelected(false);
                    controller.getSettings().set(Settings.COLOR_MODEL, "SplitColorModel");
                }

                controller.updateFromSettings();
                controller.getImageManager().repaintNewImage();
                System.out.println("Loaded Orgs " + controller.getGameModel().getSystem().getCreatures().size());
            }
            catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(controller.getGameFrame(),
                        "Load Error",
                        ex.toString(),
                        JOptionPane.ERROR_MESSAGE);
            }
            colorKind=(colorKind+2)%3;
        }
    } 
}
