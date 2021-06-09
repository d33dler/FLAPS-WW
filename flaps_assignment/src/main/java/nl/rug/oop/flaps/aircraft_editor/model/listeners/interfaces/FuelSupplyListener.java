package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

public interface FuelSupplyListener {
    default void fireFuelSupplyUpdate(AircraftDataTracker dataTracker) {
        dataTracker.performDepartureValidationCheck();
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData();
    }
}
