package nl.rug.oop.flaps.simulation.model.aircraft;

import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.util.Objects;

@Getter
@Setter
public abstract class Compartment {
    protected String name;
    protected double x;
    protected double y;

    public Point2D.Double getCoords() {
        return new Point2D.Double(x, y);
    }

    public double getCoordsHash() {
        return (x * 1000 + y);
    }
}
