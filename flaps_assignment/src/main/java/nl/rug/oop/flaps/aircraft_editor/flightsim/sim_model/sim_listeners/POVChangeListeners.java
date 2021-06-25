package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.sim_listeners;

import gov.nasa.worldwind.geom.Position;

public interface POVChangeListeners {
    void firePOVChangeUpdate(Position currentEyePos);
}
