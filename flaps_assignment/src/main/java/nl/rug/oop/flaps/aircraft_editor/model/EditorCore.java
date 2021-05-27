package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.view.BlueprintDisplay;
import nl.rug.oop.flaps.aircraft_editor.view.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

import java.awt.geom.Point2D;
import java.util.*;

@Getter
@Setter
public class EditorCore {
    private final Aircraft aircraft;

    private static double AIRCRAFT_LEN;
    private final BlueprintSelectionModel selectionModel;
    private static final double NEARBY_UNIT_RANGE = 250.0;
    private EditorFrame editorFrame;
    private NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap = new TreeMap<>();
    protected HashMap<Integer, Point2D.Double> localCoords = new HashMap<>();

    public EditorCore(Aircraft aircraft, BlueprintSelectionModel selectionModel, EditorFrame editorFrame) {
        this.aircraft = aircraft;
        this.editorFrame = editorFrame;
        this.selectionModel = selectionModel;
        updateCompartmentCoords();
        listToCoordsMap(this.aircraft.getType().getCargoAreas());
        listToCoordsMap(this.aircraft.getType().getFuelTanks());
    }

    public Optional<Compartment> extractApproxArea(Point2D.Double coords) {
        NavigableMap<Double, Compartment> xAxis;
        try {
            if (this.areasMap.lowerEntry(coords.x).getValue() == null) {
                xAxis = this.areasMap.higherEntry(coords.x).getValue();
            } else {
                xAxis = this.areasMap.lowerEntry(coords.x).getValue();
            }
            if (xAxis.lowerEntry(coords.y).getValue() == null) {
                return Optional.ofNullable(xAxis.higherEntry(coords.y).getValue());
            } else {
                return Optional.ofNullable(xAxis.lowerEntry(coords.y).getValue());
            }
        } catch (NullPointerException e) {
            System.out.println("Your cursor is outside of the blueprint's coordinate area range.");
            return Optional.empty();
        }
    }

    private void listToCoordsMap(List<? extends Compartment> list) {
        list.forEach(area -> {
            Point2D.Double pos = this.localCoords.get(area.hashCode());
            if (this.areasMap.containsKey(pos.x)) {
                this.areasMap.get(pos.x).put(pos.y, area);
            } else {
                NavigableMap<Double, Compartment> mapY = new TreeMap<>();
                mapY.put(pos.y, area);
                this.areasMap.put(pos.x, mapY);
            }
        });
    }
    private void updateCompartmentCoords() {
        AIRCRAFT_LEN = this.aircraft.getType().getLength();
        updateXY(this.aircraft.getType().getCargoAreas());
        updateXY(this.aircraft.getType().getFuelTanks());
    }

    private void updateXY(List<? extends Compartment> units) {
        units.forEach(area -> {
            var p = remap(area.getCoords());
            this.localCoords.put(area.hashCode(),p);
        });
    }

    protected Point2D.Double remap(Point2D.Double source) {
        double bpSize = BlueprintDisplay.WIDTH;
        double s = BlueprintDisplay.MK_SIZE;
        double x = (bpSize / 2) + (source.x * (bpSize / AIRCRAFT_LEN)) - s / 2;
        double y = (source.y) * (bpSize / AIRCRAFT_LEN) - s / 2;
        return new Point2D.Double(x, y);
    }
}

