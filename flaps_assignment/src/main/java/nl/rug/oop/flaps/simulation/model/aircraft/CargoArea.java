package nl.rug.oop.flaps.simulation.model.aircraft;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import java.util.Objects;

/**
 * Represents a cargo area. Note that this class does not contain any information about the actual contents of the cargo
 * area. This is done in the aircraft itself.
 *
 * @author T.O.W.E.R.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CargoArea extends Compartment {
    /**
     * The maximum dimension in meters that this cargo area can contain. You do not have to use this
     */
    private double maxSize;

    /**
     * The maximum weight in kg that the contents of this cargo may weigh
     */
    private double maxWeight;

    String caller_Id = EditorCore.cargoListenerID;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoArea cargoArea = (CargoArea) o;
        return Objects.equals(name, cargoArea.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public double requestCapacity() {
        return maxWeight;
    }
}
