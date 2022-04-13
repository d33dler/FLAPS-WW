package nl.rug.oop.flaps.simulation.model.cargo;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.FlapsDatabases;

/**
 * Represents a unit of cargo
 *
 * @author T.O.W.E.R.
 */
@Getter
@FlapsDatabases
public class CargoUnit {
    /**
     * The type that this cargo unit has
     */
    private final CargoType cargoType;
    /**
     * The weight in kg of this cargo unit
     */
    @Setter
    private double weight;

    public CargoUnit(CargoType cargoType) {
        this.cargoType = cargoType;
    }
}
