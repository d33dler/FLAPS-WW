package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

public interface CargoUnitsListener {
    default void unitAdded(Aircraft aircraft, CargoType cargoType, int amount) {}
    default void unitRemoved(Aircraft aircraft, CargoType cargoType, int amount) {}
    default void notifyChange(Aircraft aircraft) {}
}
