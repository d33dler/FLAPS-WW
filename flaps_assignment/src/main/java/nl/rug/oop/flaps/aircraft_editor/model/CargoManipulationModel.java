package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.view.CargoSettings;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
public class CargoManipulationModel implements CargoManipulationInterface{
    private CargoType cargoUnit = null;
    private CargoFreight cargoFreight = null;
    private int amount;
    private List<CargoUnitsListener> listenerSet;

    public CargoManipulationModel() {
        listenerSet = new ArrayList<>() {
        };
    }

    public void addListener(CargoUnitsListener listener) {
        this.listenerSet.add(listener);
    }

    @Override
    public void fireUpdate(Aircraft aircraft, CargoType unit, int amount) {
        this.cargoUnit = unit;
        this.amount = amount;
        this.listenerSet.forEach(listener -> {
            listener.notifyChange(aircraft);
        });
    }

    public void fireUpdate(Aircraft aircraft, CargoFreight freight, int amount) {
        this.cargoFreight = freight;
        this.amount = amount;
        this.listenerSet.forEach(listener -> {
            listener.notifyChange(aircraft);
        });
    }
}
