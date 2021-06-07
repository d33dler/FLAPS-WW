package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

public interface FuelSupplyListener {
    default void fireFuelSupplyUpdate(Aircraft aircraft, AircraftDataTracker dataTracker) {
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData();
    }
}
