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

    private int healthFan,
            healthCompressor,
            healthNozzle,
            healthTurbines,
            healthCombustor;


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
        return (float)(healthCombustor+
                healthFan+
                healthNozzle+
                healthTurbines+
                healthCompressor)/5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Engine engine = (Engine) o;
        return Objects.equals(name, engine.name);
    }
}
