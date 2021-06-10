package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

public interface BlueprintSelectionListener {
    default void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData(area);
    }
}
