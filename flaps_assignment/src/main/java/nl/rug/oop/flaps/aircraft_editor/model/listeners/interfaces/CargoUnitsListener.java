package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

public interface CargoUnitsListener {
    default void fireCargoTradeUpdate(Aircraft aircraft, AircraftDataTracker dataTracker) {
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData();
    }
}
