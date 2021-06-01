package nl.rug.oop.flaps.aircraft_editor.model;

import nl.rug.oop.flaps.aircraft_editor.controller.actions.CargoUnitsListener;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;

import java.util.HashSet;
import java.util.Set;

public class CargoManipulationModel {
    private CargoUnit cargoUnit = null;
    private double weight;
    private final Set<CargoUnitsListener> listenerSet;

    public CargoManipulationModel() {
        listenerSet = new HashSet<>();
    }

    public void addListener(CargoUnitsListener listener) {
        this.listenerSet.add(listener);
    }

    public void addUnit(Aircraft aircraft, CargoUnit unit, double weight) {
        this.cargoUnit = unit;
        this.listenerSet.forEach(listener -> {
            listener.unitAdded(aircraft, unit, weight);
        });
    }

    public void removeUnit(Aircraft aircraft, CargoUnit unit, double weight) {
        this.cargoUnit = unit;
        this.listenerSet.forEach(listener -> {
            listener.unitRemoved(aircraft, unit, weight);
        });
    }

    public void notifyMembers(Aircraft aircraft) {
        this.listenerSet.forEach(listener -> {
            listener.notifyChange(aircraft);
        });
    }
}
