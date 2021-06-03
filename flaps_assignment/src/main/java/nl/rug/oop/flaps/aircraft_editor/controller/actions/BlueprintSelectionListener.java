package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

public interface BlueprintSelectionListener {
    default void compartmentSelected(Compartment area) { }
    default void compartmentSelected(CargoArea area) { }
    default void compartmentSelected(FuelTank area) { }
}
