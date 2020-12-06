package com.victor.action;

import com.victor.io.GrowLifeGifWriter;
import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//导出动图事件
@SuppressWarnings("serial")
public class ExportGifAction extends AbstractAction {
    
    protected PanelController controller;
    protected String defaultFileName = "victor";
    
    private JFileChooser chooser = null;
    private Runnable stopRecordingCallback = null;
    
    public ExportGifAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
        
    }
    
    public ExportGifAction(PanelController controller) {
        this(controller, "保存GIF");
    }
    //设置默认动图文件名
    public void setDefaultFileName(String defaultFileName) {
        this.defaultFileName=defaultFileName;
        if (chooser!=null) {
            chooser.setSelectedFile(new File(defaultFileName));
        }
    }
    //初始化录制
    public void initChooser() {
        if (chooser!=null) return;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setAcceptAllFileFilterUsed(false);
        String date = new SimpleDateFormat(" yyyy-MM-dd").format(new Date());
        defaultFileName+=date+".gif";
        chooser.setSelectedFile(new File(defaultFileName));
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".gif")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "gif files (*.gif)";
            }
        });
    }
    //执行action
    public void actionPerformed(ActionEvent e) {
        if (stopRecordingCallback!=null) {
            stopRecordingCallback.run();
            stopRecordingCallback=null;
            this.putValue(NAME, "保存为GIF");
            controller.getGameToolbar().getGifStopRecordingButton().setVisible(false);
            return;
        }

        initChooser();
        controller.getScrollController().updateScrollBars();
        controller.setPlayGame(false);

        int returnVal = chooser.showSaveDialog(controller.getGameFrame());
        if (returnVal != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File saveFile = chooser.getSelectedFile();
        String fileName = saveFile.getName();
        if (fileName.indexOf(".") < 0) {
            try {
                String filePath = saveFile.getPath();
                saveFile = new File(filePath + ".gif");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        try {
            stopRecordingCallback = GrowLifeGifWriter.saveImage(saveFile, controller);
        } catch (Exception ex) {    //抛出异常
            ex.printStackTrace();
            JOptionPane.showMessageDialog(controller.getGameFrame(), ex.getMessage());
        }

        this.putValue(NAME, "停止GIF录制");
        controller.getGameToolbar().getGifStopRecordingButton().setVisible(true);
        controller.getGameToolbar().getGifStopRecordingButton().setText("GIF停止录制.");
    }
}
