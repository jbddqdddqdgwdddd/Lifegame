package com.victor.model.seed;

import java.awt.Point;
import com.victor.model.RotationG;
import com.victor.model.Rotation;

//种子类：细胞繁衍后的集合
public class Seed {

    protected SeedGrowPattern pattern;   //种子模式
    public Point position = null;    //初始位置default为null
    public Rotation rotation;        //角度和镜像
    public int seedBorder = 1;       //边界
    public Point parentPosition;     //上代种子位置

    public Seed(SeedGrowPattern pattern, Rotation r) {
        this.pattern = pattern;
        this.rotation = r;
    }
    public Seed(SeedGrowPattern pattern) {
        this(pattern, RotationG.get());
    }

    //获取角度和镜像状态
    public Rotation getRotation() {
        return rotation;
    }
    //获取种子位置
    public Point getPosition() {
        return position;
    }
    //设置种子位置
    public void setPosition(int x, int y) {
        this.position = new Point(x,y);
    }
    //获取种子边界
    public int getSeedBorder() {
        return seedBorder;
    }
    //设置种子边界
    public void setSeedBorder(int b) {
        this.seedBorder = b;
    }
    //获取上代位置
    public Point getParentPosition() {
        return parentPosition;
    }
    //设置上代位置
    public void setParentPosition(Point parentPosition) {
        this.parentPosition = parentPosition;
    }
    //获取种子繁衍生长模式
    public SeedGrowPattern getSeedGrowPattern() {
        return pattern;
    }
    //获取种子模式
    public BitPattern getSeedPattern() {
        return pattern.getSeedPattern();
    }
    //获取种子繁衍生长的位图模式
    public BitPattern getGrowPattern() {
        return pattern.getGrowPattern();
    }

    public boolean getSeedBit(int x, int y) {
        return getSeedPattern().getBit(x, y, getRotation());
    }

    public boolean getGrowBit(int x, int y) {
        return getGrowPattern().getBit(x, y, getRotation());
    }

    public int getSeedWidth() {
        return getSeedPattern().getWidth(getRotation());
    }

    public int getSeedHeight() {
        return getSeedPattern().getHeight(getRotation());
    }

    public int getGrowWidth() {
        return getGrowPattern().getWidth(getRotation());
    }

    public int getGrowHeight() {
        return getGrowPattern().getHeight(getRotation());
    }

    public Point getGrowOffset() {
        return RotationG.offsetToBoard(
                pattern.getGrowOffset(),
                getSeedPattern(),
                getGrowPattern(),
                getRotation());
    }

    public Point getGrowPosition() {
        Point sproutOffset = getGrowOffset();
        return new Point(position.x + sproutOffset.x, position.y + sproutOffset.y);
    }

    public Point getGrowCenter() {
        Point sproutPosition = getGrowPosition();
        Point sproutCenter = getGrowPattern().getCenter(getRotation());

        int ssX = sproutPosition.x+sproutCenter.x;
        int ssY = sproutPosition.y+sproutCenter.y;

        return new Point(ssX, ssY);
    }

    public Point getSeedOnBit() {
        return pattern.getSeedPattern().getOnBit(getRotation());
    }

    public Point getSeedOnPosition() {
        Point pos = this.getPosition();
        Point onOffset = this.getSeedOnBit();
        return new Point(pos.x+onOffset.x,pos.y+onOffset.y);
    }
}
