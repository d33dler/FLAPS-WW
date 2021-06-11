package nl.rug.oop.flaps.simulation.view.shapes;

import java.awt.*;
import java.awt.geom.*;

/**
 * RoundPolygon class - creates custom polygon shapes based on input parameters;
 * This class was sourced from  http://java-sl.com/shapes.html .
 * @Author Stanislav Lapitsky
 */

/**
 * We took the artistic freedom of sharing his shape creation classes,
 * since it was not part of the FLAPS requirements and does not involve functionality features.
 */
public class RoundPolygon implements Shape {
    GeneralPath path;

    public RoundPolygon(Polygon p, int arcWidth) {
        path=new GeneralPath();
        transform(p, arcWidth, path);
    }
    public Rectangle getBounds() {
        return path.getBounds();
    }

    public Rectangle2D getBounds2D() {
        return path.getBounds2D();
    }

    public boolean contains(double x, double y) {
        return path.contains(x,y);
    }

    public boolean contains(Point2D p) {
        return path.contains(p);
    }

    public boolean intersects(double x, double y, double w, double h) {
        return path.intersects(x,y,w,h);
    }

    public boolean intersects(Rectangle2D r) {
        return path.intersects(r);
    }

    public boolean contains(double x, double y, double w, double h) {
        return path.contains(x,y,w,h);
    }

    public boolean contains(Rectangle2D r) {
        return path.contains(r) ;
    }

    public PathIterator getPathIterator(AffineTransform at) {
        return path.getPathIterator(at);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return path.getPathIterator(at, flatness);
    }

    protected static void transform(Polygon shape, int arcWidth, GeneralPath path) {
            PathIterator pIter = shape.getPathIterator(new AffineTransform());

            Point2D.Float pointFirst=new Point2D.Float(0,0);
            Point2D.Float pointSecond=null;

            Point2D.Float pointLast=new Point2D.Float(0,0);
            Point2D.Float pointCorner=null;
            Point2D.Float pointNext=null;

            float [] coor = new float[6];
            while (!pIter.isDone()) {
                int type = pIter.currentSegment(coor);
                float x1 = coor[0];
                float y1 = coor[1];
                float x2 = coor[2];
                float y2 = coor[3];
                float x3 = coor[4];
                float y3 = coor[5];

                switch (type) {
                    case PathIterator.SEG_CLOSE:
                        break;
                    case PathIterator.SEG_CUBICTO:
                        path.curveTo(x1, y1, x2, y2, x3, y3);
                        break;
                    case PathIterator.SEG_LINETO:
                        if (pointCorner==null) {
                            pointCorner=new Point2D.Float(x1,y1);
                            if (pointNext==null) {
                                pointSecond=new Point2D.Float(x1,y1);
                                Point2D.Float arcStartPoint=getArcPoint(pointSecond, pointFirst, arcWidth);

                                path.moveTo(arcStartPoint.x,arcStartPoint.y);
                            }
                        }
                        else {
                            pointNext=new Point2D.Float(x1,y1);
                            add(path, pointLast, pointCorner, pointNext, arcWidth);
                            pointLast=pointCorner;
                            pointCorner=pointNext;
                        }
                        break;
                    case PathIterator.SEG_MOVETO:
                        pointLast.x=x1;
                        pointLast.y=y1;
                        pointFirst.x=x1;
                        pointFirst.y=y1;
                        break;
                    case PathIterator.SEG_QUADTO:
                        path.quadTo(x1, y1, x2, y2);
                        break;
                }
                pIter.next();
            }

            add(path, pointLast, pointCorner, pointFirst, arcWidth);
            add(path, pointCorner, pointFirst, pointSecond, arcWidth);
            path.closePath();

    }

    protected static void add(GeneralPath path, Point2D.Float last, Point2D.Float corner, Point2D.Float next, float w) {
        Point2D.Float arcStartPoint=getArcPoint(last, corner, w);
        Point2D.Float arcEndPoint=getArcPoint(next, corner, w);

        path.lineTo(arcStartPoint.x, arcStartPoint.y);
        path.quadTo(corner.x, corner.y, arcEndPoint.x, arcEndPoint.y);
    }

    protected static Point2D.Float getArcPoint(Point2D.Float p1, Point2D.Float p2, float w) {
        Point2D.Float res=new Point2D.Float();
        float d=Math.round(Math.sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y)));
        if (p1.x<p2.x) {
            res.x = p2.x - w * Math.abs(p1.x - p2.x) / d;
        }
        else {
            res.x = p2.x + w * Math.abs(p1.x - p2.x) / d;
        }

        if (p1.y<p2.y) {
            res.y = p2.y - w * Math.abs(p1.y - p2.y) / d;
        }
        else {
            res.y = p2.y + w * Math.abs(p1.y - p2.y) / d;
        }

        return res;
    }

    protected static void transformPrint(Polygon shape) {
            PathIterator pIter = shape.getPathIterator(new AffineTransform());
            GeneralPath res = new GeneralPath();

            float [] coor = new float[6];
            while (!pIter.isDone()) {
                int type = pIter.currentSegment(coor);
                float x1 = coor[0];
                float y1 = coor[1];
                float x2 = coor[2];
                float y2 = coor[3];
                float x3 = coor[4];
                float y3 = coor[5];

                switch (type) {
                    case PathIterator.SEG_CLOSE:
                        res.closePath();
                        break;
                    case PathIterator.SEG_CUBICTO:
                        res.curveTo(x1, y1, x2, y2, x3, y3);
                        break;
                    case PathIterator.SEG_LINETO:
                        res.lineTo(x1, y1);
                        break;
                    case PathIterator.SEG_MOVETO:
                        res.moveTo(x1, y1);
                        break;
                    case PathIterator.SEG_QUADTO:
                        res.quadTo(x1, y1, x2, y2);
                        break;
                }
                pIter.next();
            }

    }
}
