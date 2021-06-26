package nl.rug.oop.flaps.aircraft_editor.model.listener_models;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.PassengerListener;

public class PassengerRegModel implements PassengerListener {


    @Override
    public void firePassUpdate(AircraftDataTracker dataTracker) {
        PassengerListener.super.firePassUpdate(dataTracker);
    }
}
