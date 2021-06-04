package nl.rug.oop.flaps.simulation.model.aircraft;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import java.awt.geom.Point2D;
import java.util.Objects;

@Getter
@Setter
public abstract class Compartment {
    protected String name;
    protected String compartmentId;
    protected double x;
    protected double y;

    public Point2D.Double getCoords() {
        return new Point2D.Double(x, y);
    }
    public int getCoordsHash() {
        return Objects.hash(x,y);
    }
}
