package com.victor.model;

//控制角度和镜像
public class Rotation {

    private int angle; // 0代表0度 1代表90度 2代表180度 3代表270度
    private boolean mirror;

    public Rotation(int angle, boolean mirror){
        this.angle = angle;
        this.mirror = mirror;
    }

    //获取angle 和 mirror状态
    public int getAngle() { return angle; }
    public boolean isMirror(){ return mirror; }

}
