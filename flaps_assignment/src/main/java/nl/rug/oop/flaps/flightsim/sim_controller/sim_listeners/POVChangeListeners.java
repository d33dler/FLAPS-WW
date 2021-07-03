package nl.rug.oop.flaps.flightsim.sim_controller.sim_listeners;

import gov.nasa.worldwind.geom.Position;

public interface POVChangeListeners {
    void firePOVChangeUpdate(Position currentEyePos);
}
