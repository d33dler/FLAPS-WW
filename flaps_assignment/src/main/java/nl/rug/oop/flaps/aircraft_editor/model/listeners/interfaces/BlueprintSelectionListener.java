package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

/**
 * Interface with default method and its implementation;
 * Used to avoid code duplication and store default update method routine in a single location;
 */

public interface BlueprintSelectionListener {
    default void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData(area);
    }
}
