package nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

public interface PassengerListener extends ChangeListener {
    default void firePassUpdate(AircraftDataTracker dataTracker){}
}
