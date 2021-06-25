package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

/**
 * Interface with default method and its implementation;
 * Used to avoid code duplication and store default update method routine in a single location;
 */

public interface BlueprintSelectionListener extends ChangeListener {

    default void fireBpUpdate(Compartment area, AircraftDataTracker dataTracker) {
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData(area);
    }
}
