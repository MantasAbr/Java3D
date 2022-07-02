package main.point;

import main.Display;
import java.awt.*;

public class PointConverter {

    /* 3D  |  2D */
    /*  Z  =  Y  */
    /*  Y  =  X  */
    /*  X  =  0  */

    private static double scale = 1;
    private final static double zoom = 1.2;

    public static void zoomIn(){
        scale += zoom;
    }

    public static void zoomOut(){
        scale /= zoom;
    }

    public static Point convertToTwoDimension(PointThree point3D) {
        double x3d = point3D.y * scale;
        double y3d = point3D.z * scale;
        double depth = point3D.x * scale;
        double[] newValues = scale(x3d, y3d, depth);

        int x2d = (int)(Display.WINDOW_WIDTH / 2 + newValues[0]);
        int y2d = (int)(Display.WINDOW_HEIGHT / 2 - newValues[1]);

        Point point2D = new Point(x2d, y2d);
        return point2D;
    }

    private static double[] scale(double x3d, double y3d, double depth) {
        double distance = Math.sqrt(Math.pow(x3d, 2) + Math.pow(y3d, 2));
        double theta = Math.atan2(y3d, x3d);
        double depthToObject = 15 - depth;
        double localScale = Math.abs(1400/(depthToObject+1400));
        distance *= localScale;

        double[] newValues = new double[2];
        newValues[0] = distance * Math.cos(theta);
        newValues[1] = distance * Math.sin(theta);
        return newValues;
    }

    public static void rotateAxisX(PointThree p, boolean clockWise, double degrees) {
        double radius = Math.sqrt(Math.pow(p.y, 2) + Math.pow(p.z, 2));
        double theta = Math.atan2(p.z, p.y);

        theta += 2 * Math.PI / 360 * degrees * (clockWise ? -1 : 1);
        p.y = radius * Math.cos(theta);
        p.z = radius * Math.sin(theta);
    }

    public static void rotateAxisY(PointThree p, boolean clockWise, double degrees) {
        double radius = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.z, 2));
        double theta = Math.atan2(p.x, p.z);

        theta += 2 * Math.PI / 360 * degrees * (clockWise ? -1 : 1);
        p.z = radius * Math.cos(theta);
        p.x = radius * Math.sin(theta);
    }

    public static void rotateAxisZ(PointThree p, boolean clockWise, double degrees) {
        double radius = Math.sqrt(Math.pow(p.x, 2) + Math.pow(p.y, 2));
        double theta = Math.atan2(p.y, p.x);

        theta += 2 * Math.PI / 360 * degrees * (clockWise ? -1 : 1);
        p.x = radius * Math.cos(theta);
        p.y = radius * Math.sin(theta);
    }
}
