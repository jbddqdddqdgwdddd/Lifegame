package com.victor.model;

import java.awt.Point;

import com.victor.model.seed.BitPattern;

//存放Rotation们的group类
public class RotationG {
    //一个存放Rotation类的数组
    private static Rotation[][] rotations = new Rotation[4][2];
    //获取Rotation
    public static Rotation get(int angle, boolean mirror) {
        Rotation r = rotations[angle][mirror?0:1];
        //如果索引为空，将增加rotation进入数组
        if (r==null) {
            r = new Rotation(angle, mirror);
            rotations[angle][mirror?0:1]=r;
        }
        //返回rotation
        return r;
    }
    //重载
    public static Rotation get(int angle) {
        return get(angle, false);
    }
    public static Rotation get() {
        return get(0, false);
    }


    public static Point fromBoard(Point point, BitPattern bp1, Rotation r) {

        if (r.isMirror()) {
            point = new Point (bp1.getWidth(r)-point.x-1,  point.y);
        }
        switch (r.getAngle()) {

            case 1: return new Point(bp1.getHeight(r)-point.y-1, point.x);

            case 2: return new Point(bp1.getWidth(r)-point.x-1, bp1.getHeight(r)-point.y-1);

            case 3: return new Point(point.y, bp1.getWidth(r)-point.x-1);

            //Case 0:
            default: return point;

        }
    }
    public static Point fromBoard(Point point, Rotation r) {
        if (r.isMirror()) {
            point = new Point(-point.x, point.y);
        }
        switch (r.getAngle()) {
            // case 0:
            default:
                return new Point(point.x, point.y);

            case 1:
                return new Point(-point.y, point.x);
            case 2:
                return new Point(-point.x, -point.y);
            case 3:
                return new Point(point.y, -point.x);

        }
    }

    public static Point toBoard(Point point, BitPattern bp1, Rotation r) {
        if (!r.isMirror()) { //不是镜像
            switch (r.getAngle()) {
                //90度
                case 1: return new Point(point.y, bp1.getWidth()-point.x-1);
                //180度
                case 2: return new Point(bp1.getWidth()-point.x-1, bp1.getHeight()-point.y-1);
                //270度
                case 3: return new Point(bp1.getHeight()-point.y-1, point.x);
                //默认0度
                default: return point;

            }
        }
        else {
            //镜像情况，翻转x轴坐标
            switch (r.getAngle()) {

                case 1: return new Point(bp1.getHeight()-point.y-1, bp1.getWidth()-point.x-1);

                case 2: return new Point(point.x, bp1.getHeight()-point.y-1);

                case 3: return new Point(point.y, point.x);

                default: return new Point(bp1.getWidth()-point.x-1, point.y);

            }
        }
    }
    public static Point toBoard(Point point, Rotation r) {
        if (!r.isMirror()) {
            switch (r.getAngle()) {
                // case 0:
                default:
                    return new Point(point.x, point.y);
                case 1:
                    return new Point(point.y, -point.x);
                case 2:
                    return new Point(-point.x, -point.y);
                case 3:
                    return new Point(-point.y, point.x);

            }
        }
        else { // mirror == true
            switch (r.getAngle()) {
                // case 0:
                default:
                    return new Point(-point.x, point.y);

                case 1:
                    return new Point(-point.y, -point.x);
                case 2:
                    return new Point(point.x, -point.y);
                case 3:
                    return new Point(point.y, point.x);
            }
        }
    }
    //转化坐标
    public static Point translate(Point p, int tx, int ty) {
        return new Point(p.x+tx,p.y+ty);
    }
    public static Point translate(Point p, Point t) {
        return translate(p, t.x, t.y);
    }

    public static Point offsetToBoard(Point offset, BitPattern bp1, BitPattern bp2, Rotation r) {
        Point p2a = offset;
        Point p2b = translate(offset, bp2.getWidth() - 1, 0);
        Point p2c = translate(offset, 0, bp2.getHeight() - 1);
        Point p2d = translate(offset, bp2.getWidth() - 1, bp2.getHeight() - 1);

        Point rp2a = toBoard(p2a, bp1, r);
        Point rp2b = toBoard(p2b, bp1, r);
        Point rp2c = toBoard(p2c, bp1, r);
        Point rp2d = toBoard(p2d, bp1, r);

        int minx = Math.min(Math.min(rp2a.x, rp2b.x), Math.min(rp2c.x, rp2d.x));
        int miny = Math.min(Math.min(rp2a.y, rp2b.y), Math.min(rp2c.y, rp2d.y));
        return new Point(minx, miny);
    }


}
