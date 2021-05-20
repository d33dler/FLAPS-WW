package nl.rug.oop.flaps.simulation.model.cargo;

import lombok.Getter;

/**
 * Represents a unit of cargo
 *
 * @author T.O.W.E.R.
 */
@Getter
public class CargoUnit {
    /**
     * The type that this cargo unit has
     */
    private final CargoType cargoType;
    /**
     * The weight in kg of this cargo unit
     */
    private double weight;

    public CargoUnit(CargoType cargoType) {
        this.cargoType = cargoType;
    }
}
