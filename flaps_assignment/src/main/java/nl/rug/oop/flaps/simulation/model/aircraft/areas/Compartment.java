package nl.rug.oop.flaps.simulation.model.aircraft.areas;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

import java.awt.geom.Point2D;
import java.util.Objects;

/**
 * Compartment abstract class is used to force method implementation upon aircraft area types classes;
 */
@Getter
@Setter
public abstract class Compartment {
    protected String name;
    protected String compartmentId;
    protected double x;
    protected double y;
    protected double loadWeight = 0;
    public abstract float requestCapacity();

    public abstract void getUpdateAreaLoad(AircraftDataTracker dataTracker);


    public Point2D.Double getCoords() {
        return new Point2D.Double(x, y);
    }

    public int getCoordsHash() {
        return Objects.hash(x, y);
    }

}
