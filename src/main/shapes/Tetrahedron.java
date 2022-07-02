package main.shapes;

import java.awt.*;

public class Tetrahedron {

    private Polygons[] polygons;
    private Color color;

    public Tetrahedron(Color color, Polygons... polygons) {
        this.color = color;
        this.polygons = polygons;
        this.setPolygonColor();
    }

    public Tetrahedron(Polygons... polygons) {
        this.color = Color.WHITE;
        this.polygons = polygons;
    }


    public void render(Graphics g){
        for (Polygons poly : this.polygons){
            poly.render(g);
        }
    }

    public void rotate(boolean clockWise, double xDegrees, double yDegrees, double zDegrees) {
        for(Polygons p : this.polygons) {
            p.rotate(clockWise, xDegrees, yDegrees, zDegrees);
        }
        this.sortPolygons();
    }

    public void sortPolygons() {
        Polygons.sortPolygons(this.polygons);
    }

    private void setPolygonColor() {
        for(Polygons poly : this.polygons){
            poly.setColor(this.color);
        }
    }
}
