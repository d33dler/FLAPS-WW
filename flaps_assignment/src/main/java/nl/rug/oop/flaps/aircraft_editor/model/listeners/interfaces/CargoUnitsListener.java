package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

/**
 * Interface with default method and its implementation;
 * Used to avoid code duplication and store default update method routine in a single location;
 */

public interface CargoUnitsListener extends ChangeListener {
    default void fireCargoUpdate(AircraftDataTracker dataTracker) {
        ChangeListener.super.defaultUpdate(dataTracker);
    }

}
