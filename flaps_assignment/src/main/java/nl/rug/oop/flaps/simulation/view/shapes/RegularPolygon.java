package nl.rug.oop.flaps.simulation.view.shapes;

import java.awt.*;

/**
 * RegularPolygon class - creates custom polygon shapes based on input parameters;
 * This class was sourced from  http://java-sl.com/shapes.html .
 * @Author Stanislav Lapitsky;
 */

/**
 * We took the artistic freedom of sharing his shape creation classes,
 * since it was not part of the FLAPS requirements and does not involve functionality features.
 */
public class RegularPolygon extends Polygon {
    public RegularPolygon(int x, int y, int r, int vertexCount) {
        this(x, y, r, vertexCount, 0);
    }
    public RegularPolygon(int x, int y, int r, int vertexCount, double startAngle) {
        super(getXCoordinates(x, y, r, vertexCount, startAngle)
              ,getYCoordinates(x, y, r, vertexCount, startAngle)
              ,vertexCount);
    }

    protected static int[] getXCoordinates(int x, int y, int r, int vertexCount, double startAngle) {
        int res[]=new int[vertexCount];
        double addAngle=2*Math.PI/vertexCount;
        double angle=startAngle;
        for (int i=0; i<vertexCount; i++) {
            res[i]=(int)Math.round(r*Math.cos(angle))+x;
            angle+=addAngle;
        }
        return res;
    }

    protected static int[] getYCoordinates(int x, int y, int r, int vertexCount, double startAngle) {
        int res[]=new int[vertexCount];
        double addAngle=2*Math.PI/vertexCount;
        double angle=startAngle;
        for (int i=0; i<vertexCount; i++) {
            res[i]=(int)Math.round(r*Math.sin(angle))+y;
            angle+=addAngle;
        }
        return res;
    }
}
