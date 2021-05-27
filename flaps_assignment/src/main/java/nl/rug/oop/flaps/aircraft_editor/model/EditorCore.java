package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.view.BlueprintDisplay;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

import java.awt.geom.Point2D;
import java.util.*;

@Getter
@Setter
public class EditorCore {
    private final Aircraft aircraft;
    private NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap = new TreeMap<>();
    private static double AIRCRAFT_LEN;
    private final BlueprintSelectionModel selectionModel;
    private static final double NEARBY_UNIT_RANGE = 250.0;

    public EditorCore(Aircraft aircraft, BlueprintSelectionModel selectionModel) {
        this.aircraft = aircraft;
        this.selectionModel = selectionModel;
        updateUnitCoords();
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
            if (this.areasMap.containsKey(area.getX())) {
                this.areasMap.get(area.getX()).put(area.getY(), area);
            } else {
                NavigableMap<Double, Compartment> mapY = new TreeMap<>();
                mapY.put(area.getY(), area);
                this.areasMap.put(area.getX(), mapY);
            }
        });
    }
    private void updateUnitCoords() {
        if (!this.aircraft.isUpdatedUnitXY()) {
            AIRCRAFT_LEN = this.aircraft.getType().getLength();
            updateXY(this.aircraft.getType().getCargoAreas());
            updateXY(this.aircraft.getType().getFuelTanks());
            this.aircraft.setUpdatedUnitXY(true);
        }
    }

    private void updateXY(List<? extends Compartment> units) {
        units.forEach(cargoArea -> {
            var p = remap(cargoArea.getCoords());
            cargoArea.setX(p.x);
            cargoArea.setY(p.y);
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

