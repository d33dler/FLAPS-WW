package nl.rug.oop.flaps.simulation.model.loaders;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

import java.util.Set;

@Setter
@Getter
public class DataHolder {
    private Compartment compartment = null;
    private CargoArea cargoArea = null;
    private FuelTank fuelTank = null;
    private Set<CargoFreight> cargoFreightSet;
    private double level;

    public DataHolder() {
    }
}
