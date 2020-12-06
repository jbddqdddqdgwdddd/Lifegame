package com.victor.model.seed;

import java.awt.Point;
import com.victor.model.RotationG;
import com.victor.model.Rotation;

public class Symmetric extends Seed{
    public Symmetric(SeedGrowPattern pattern) {
        super(pattern);
    }

    //重写获取角度和镜像的函数
    public Rotation getRotation() {
        if (getParentPosition()==null) {
            return RotationG.get(0, false);
        }
        int ox = getParentPosition().x;
        int oy = getParentPosition().y;

        Point farCorner = getFarCorner();

        if(farCorner.x >= ox && farCorner.y > oy) {
            return RotationG.get(2, false);

        }
        if(farCorner.y >= oy && farCorner.x < ox) {
            return RotationG.get(1, false);
        }
        if(farCorner.x <= ox && farCorner.y < oy) {
            return RotationG.get(0, false);
        }

        if(farCorner.y <= oy && farCorner.x > ox) {
            return RotationG.get(3, false);
        }
        return RotationG.get(0, false);
    }

    //获取种子坐标
    public boolean getSeedBit(int x, int y) {
        return getSeedPattern().getBit(x, y);
    }

    //获取种子扩散后的坐标
    private Point getFarCorner() {
        //上代种子坐标
        int ox = getParentPosition().x;
        int oy = getParentPosition().y;
        //种子坐标
        int x = getPosition().x;
        int y = getPosition().y;
        //获取最大繁衍坐标
        int maxX = getPosition().x+getSeedPattern().getWidth()-1;
        int maxY = getPosition().y+getSeedPattern().getHeight()-1;
        //向上繁衍
        if (maxX+x == ox*2 && maxY-oy > oy-getPosition().y) {
            y = maxY;
            return new Point(x,y);
        }
        //向右繁衍
        if (maxX+x == ox*2 && maxY-oy < oy-getPosition().y) {
            x = maxX;
            return new Point(x,y);
        }
        //向右上繁衍
        if (maxX-ox > ox-getPosition().x && maxY+y == oy*2) {
            x = maxX;
            y = maxY;
            return new Point(x,y);
        }
        //没有空间，原地
        if (maxX-ox < ox-getPosition().x && maxY+y == oy*2) {
            return new Point(x,y);
        }

        if (maxX-ox > ox-getPosition().x ) {
            x = maxX;
        }

        if (maxY-oy > oy-getPosition().y ) {
            y = maxY;
        }

        return new Point(x,y);

    }
}
