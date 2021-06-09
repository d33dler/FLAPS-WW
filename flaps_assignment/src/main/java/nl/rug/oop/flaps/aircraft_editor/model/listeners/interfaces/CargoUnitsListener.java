package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

public interface CargoUnitsListener {
    default void fireCargoTradeUpdate(AircraftDataTracker dataTracker) {
        dataTracker.performDepartureValidationCheck();
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData();
    }
}
