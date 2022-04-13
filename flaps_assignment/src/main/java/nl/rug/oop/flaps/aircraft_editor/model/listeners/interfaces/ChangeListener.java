package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

/**
 * ##Tagging interface
 */
public interface ChangeListener {
    default void defaultUpdate(AircraftDataTracker dataTracker){
        dataTracker.performDepartureValidationCheck();
        dataTracker.getDisplay().repaint();
        dataTracker.refreshData();
    }
}
