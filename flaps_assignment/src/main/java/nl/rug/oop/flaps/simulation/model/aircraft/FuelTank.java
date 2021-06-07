package nl.rug.oop.flaps.simulation.model.aircraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;

import java.util.Objects;

/**
 * Represents a fuel tank. Note that this class does not contain any information about the actual contents of the fuel
 * tank. This is done in the aircraft itself.
 *
 * @author T.O.W.E.R.
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FuelTank extends Compartment {
    /**
     * The maximum amount of fuel this tank can contain in kg
     */
    private int capacity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FuelTank fuelTank = (FuelTank) o;
        return Objects.equals(name, fuelTank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public float requestCapacity() {
        return (float)capacity;
    }

    @Override
    public void getAreaLoad(AircraftDataTracker dataTracker) {
        dataTracker.updateFuelAreaMass();
    }
}
