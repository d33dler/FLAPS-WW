package nl.rug.oop.flaps.aircraft_editor.model;

import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

public interface BlueprintSelectionListener {

    default void cargoAreaSelected(CargoArea selectedCargo) {}
    default void fuelTankSelected(FuelTank selectedFuel) {}
}
