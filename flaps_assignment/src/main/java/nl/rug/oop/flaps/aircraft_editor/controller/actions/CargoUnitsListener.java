package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;

public interface CargoUnitsListener {
    default void unitAdded(Aircraft aircraft, CargoUnit unit, Double amount) {}
    default void unitRemoved(Aircraft aircraft, CargoUnit unit, double amount) {}
    default void notifyChange(Aircraft aircraft) {}
}
