package nl.rug.oop.flaps.aircraft_editor.model;

import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

public interface CargoManipulationInterface {
    default void fireUpdate(Aircraft aircraft, CargoType unit, int amount) {}
    default void fireUpdate(Aircraft aircraft, CargoFreight unit, int amount) {}
}
