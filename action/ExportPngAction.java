package com.victor.action;

import com.victor.panel.PanelController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

//导出PNG图片事件
@SuppressWarnings("serial")
public class ExportPngAction extends AbstractAction {
    
    protected PanelController controller;
    protected String defaultFileName = "GrowLife";
    
    private JFileChooser chooser = null;

    //默认状态下的构造
    public ExportPngAction(PanelController controller, String name) {
        super(name);
        this.controller = controller;
        
    }
    
    public ExportPngAction(PanelController controller) {
        this(controller, "保存PNG");
    }
    
    public void setDefaultFileName(String defaultFileName) {
        this.defaultFileName=defaultFileName;
        if (chooser!=null) {
            chooser.setSelectedFile(new File(defaultFileName));
        }
    }
    //初始化捕捉
    public void initChooser() {
        if (chooser!=null) return;
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.setAcceptAllFileFilterUsed(false);
        String date = new SimpleDateFormat(" yyyy-MM-dd").format(new Date());
        defaultFileName+=date+".png";
        chooser.setSelectedFile(new File(defaultFileName));
        chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".png")
                        || f.isDirectory();
            }

            public String getDescription() {
                return "png files (*.png)";
            }
        });
    }
    //执行图片保存
    public void actionPerformed(ActionEvent e) {
        initChooser();
        controller.getScrollController().updateScrollBars();
        int returnVal = chooser.showSaveDialog(controller.getGameFrame());
        File saveFile;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            saveFile = chooser.getSelectedFile();
            String fileName = saveFile.getName();
            if (fileName.indexOf(".") < 0) {
                try {
                    String filePath = saveFile.getPath();
                    saveFile = new File(filePath + ".png");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try {       //抛出异常
                double zoom = controller.getBoardRenderer().getZoom();
                int width = (int) (controller.getBoardRenderer().getRendererBounds().getWidth()*zoom);
                width = Math.min(width,controller.getScrollPanel().getViewportSize().width);
                int height = (int) (controller.getBoardRenderer().getRendererBounds().getHeight()*zoom);
                height = Math.min(height,controller.getScrollPanel().getViewportSize().height);
                controller.getImageManager().saveCroppedImage(saveFile,width, height);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(controller.getGameFrame(), "图片保存失败！");
            }
        }
    }        
}
