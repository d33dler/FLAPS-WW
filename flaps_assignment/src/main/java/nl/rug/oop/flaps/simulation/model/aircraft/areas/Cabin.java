package nl.rug.oop.flaps.simulation.model.aircraft.areas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cabin extends Compartment {

    private int seats;
    @Override
    public float requestCapacity() {
        return seats;
    }

    @Override
    public void getAreaLoad(AircraftDataTracker dataTracker) {
    }
}
