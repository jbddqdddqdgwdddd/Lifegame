package com.victor.action;

import com.victor.io.GegrpIo;
import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//保存游戏数据action事件
@SuppressWarnings("serial")
public class SaveGegrpAction extends AbstractAction {
    
    protected PanelController controller;
    
    private JFileChooser chooser = null;
    
    public SaveGegrpAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
    }
    
    public SaveGegrpAction(PanelController controller) {
        this(controller, "保存生命组");
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
        String date = new SimpleDateFormat(" yyyy-MM-dd HHmm").format(new Date());
        String defaultFileName="Gegrp"+date+".txt";
        chooser.setSelectedFile(new File(defaultFileName));
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
    
    public void actionPerformed(ActionEvent e) {
        initChooser();
        controller.setPlayGame(false);
        int returnVal = chooser.showSaveDialog(controller.getGameFrame());
        File saveFile;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            String fileName = saveFile.getName();
            if (fileName.indexOf(".") < 0) {
                try {
                    String filePath = saveFile.getPath();
                    saveFile = new File(filePath + ".txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            try {
                GegrpIo.saveGegrp(saveFile, controller.getGameModel());
            }
            catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(controller.getGameFrame(),
                        "Save Error",
                        "Error saving genome",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
