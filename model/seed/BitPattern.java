package com.victor.model.seed;


import java.awt.Point;

import com.victor.model.Rotation;

public class BitPattern {
    //坐标数组
    protected int[][] bitPattern;
    //坐标状态
    protected Point onBit = null;

    public BitPattern() { }
    public BitPattern(int[][] bitPattern) {
        this.bitPattern = bitPattern;
    }
    public BitPattern(int[][] bitPattern, boolean xySwitch) {

        if (xySwitch) {
            this.bitPattern = xySwitch(bitPattern);
        }
        else {
            this.bitPattern = bitPattern;
        }

    }

    //获取宽度
    public int getWidth() {
        return bitPattern.length;
    }
    public int getWidth(Rotation r) {
        //检查是否镜像翻转
        if (r.getAngle()==0 || r.getAngle()==2) {
            return getWidth();
        }
        return getHeight();
    }

    //获取高度
    public int getHeight() {
        return bitPattern[0].length;
    }
    public int getHeight(Rotation r) {
        //检查是否镜像
        if (r.getAngle()==0 || r.getAngle()==2) {
            return getHeight();
        }
        return getWidth();
    }

    boolean getBit(int x, int y) {
        return bitPattern[x][y]==1;
    }
    //获取种子坐标数组
    boolean getBit(int x, int y, Rotation r) {
        Point rp = com.victor.model.RotationG.fromBoard(new Point(x,y), this, r);
        return getBit(rp.x, rp.y);
    }

    //获取中心点
    public Point getCenter() {
        return new Point((getWidth()-1)/2,(getHeight()-1)/2);
    }
    //镜像
    public Point getCenter(Rotation r) {
        return com.victor.model.RotationG.toBoard(getCenter(), this, r);
    }
    //获取状态
    public Point getOnBit() {

        if (this.onBit!=null) {
            return this.onBit;
        }
        else {
            for (int x=0;x<getWidth();x++) {
                for (int y=0;y<getHeight();y++) {
                    if  (getBit(x,y)) {
                        this.onBit = new Point(x,y);
                        return onBit;
                    }
                }
            }
        }
        return null;
    }
    public Point getOnBit(Rotation r) {
        return com.victor.model.RotationG.toBoard(getOnBit(), this, r);
    }
    //图形反转
    public static int[][] xySwitch(int[][] shape) {
        if (shape.length==0) {
            return new int[0][0];
        }
        int[][] newShape = new int[shape[0].length][shape.length];

        for (int i=0;i<shape.length;i++) {
            for (int j=0;j<shape[0].length;j++) {
                newShape[j][i] = shape[i][j];
            }
        }

        return newShape;

    }
}