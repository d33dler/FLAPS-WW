package nl.rug.oop.flaps.simulation.model.aircraft.areas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Engine extends Components {
    private String type, engineModel;
    private static final double MAX_HEALTH = 100.0;

    private double healthFan = MAX_HEALTH,
            healthCompressor = MAX_HEALTH,
            healthNozzle = MAX_HEALTH,
            healthTurbines = MAX_HEALTH,
            healthCombustor = MAX_HEALTH;


    @Override
    public float requestCapacity() {
        return 0;
    }

    @Override
    public void getAreaLoad(AircraftDataTracker dataTracker) {
        this.requestCapacity();
    }

    @Override
    public float getTotalHealth() {
        return (float) (healthCombustor +
                healthFan +
                healthNozzle +
                healthTurbines +
                healthCompressor) / 5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return Objects.equals(name, engine.name);
    }
}
