package main.shapes;

import main.point.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import main.point.PointConverter;

public class Polygons {

    private PointThree[] polygonPoints;
    private Color polygonColor;

    public Polygons(Color polygonColor, PointThree... points) {
        this.polygonColor = polygonColor;
        polygonPoints = new PointThree[points.length];
        for(int i = 0; i < points.length; i++){
            PointThree p = points[i];
            polygonPoints[i] = new PointThree(p.x, p.y, p.z);
        }
    }

    public void render(Graphics g) {
        Polygon polygon = new Polygon();
        for(int i = 0; i < polygonPoints.length; i++){
            Point p = PointConverter.convertToTwoDimension(polygonPoints[i]);
            polygon.addPoint(p.x, p.y);
        }
        g.setColor(polygonColor);
        g.fillPolygon(polygon);
    }

    public void rotate(boolean clockWise, double xDegrees, double yDegrees, double zDegrees) {
        for(PointThree p : polygonPoints) {
            PointConverter.rotateAxisX(p, clockWise, xDegrees);
            PointConverter.rotateAxisY(p, clockWise, yDegrees);
            PointConverter.rotateAxisZ(p, clockWise, zDegrees);
        }
    }

    public double getAverageX() {
        double sum = 0;
        for(PointThree p : this.polygonPoints) {
            sum += p.x;
        }

        return sum / this.polygonPoints.length;
    }

    public static Polygons[] sortPolygons(Polygons[] polygons) {

        ArrayList<Polygons> polygonsList = new ArrayList<Polygons>(Arrays.asList(polygons));

        Collections.sort(polygonsList, new Comparator<Polygons>() {
            @Override
            public int compare(Polygons o1, Polygons o2) {
                return o2.getAverageX() - o1.getAverageX() < 0 ? 1 : -1;
            }
        });

        for(int i = 0; i < polygons.length; i++) {
            polygons[i] = polygonsList.get(i);
        }

        return polygons;
    }

    public void setColor(Color color){
        polygonColor = color;
    }
}


