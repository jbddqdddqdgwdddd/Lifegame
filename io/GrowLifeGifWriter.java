package com.victor.io;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.victor.model.step.GameStepEvent;
import com.victor.model.step.GameStepListener;
import com.victor.panel.PanelController;
import com.victor.model.step.GameStep.StepType;

public class GrowLifeGifWriter {
    public static Runnable saveImage(File saveFile, PanelController controller) throws IOException {
        double zoom = controller.getBoardRenderer().getZoom();
        int width = (int) (controller.getBoardRenderer().getRendererBounds().getWidth()*zoom);
        width = Math.min(width,controller.getScrollPanel().getViewportSize().width);
        int height = (int) (controller.getBoardRenderer().getRendererBounds().getHeight()*zoom);
        height = Math.min(height,controller.getScrollPanel().getViewportSize().height);
        BufferedImage firstImage = controller.getImageManager().getCroppedExportImage(width, height);

        //使用最后一个参数创建一个新的BufferedOutputStream
        ImageOutputStream output = new FileImageOutputStream(saveFile);

        //创建第一个图片类型为1秒的gif序列        //在帧之间连续循环
        GifSequenceWriter writer = new GifSequenceWriter(output, firstImage.getType(), 1, true);

        //将第一个图片写出我们的序列
        writer.writeToSequence(firstImage);

        final int fwidth = width;
        final int fheight = height;
        GameStepListener gifExportGsl = new GameStepListener() {
            @Override
            public void stepPerformed(GameStepEvent event) {
                if (event.getStepType() == StepType.STEP_BUNDLE) {
                    controller.getInteractionLock().readLock().lock();
                    BufferedImage nextImage = controller.getImageManager().getCroppedExportImage(fwidth, fheight);
                    try {
                        writer.writeToSequence(nextImage);
                    }
                    catch(IOException ex) {}
                    controller.getInteractionLock().readLock().unlock();
                }
            }
        };
        controller.getGameModel().getGameThread().addGameStepListener(gifExportGsl);

        // 结束保存GIF
        return new Runnable() {
            @Override
            public void run() {
                controller.getGameModel().getGameThread().removeGameStepListener(gifExportGsl);
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException ex) {}
                try {
                    writer.close();
                    output.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    }
}
