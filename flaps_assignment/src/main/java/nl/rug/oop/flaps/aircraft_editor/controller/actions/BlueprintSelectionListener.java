package nl.rug.oop.flaps.aircraft_editor.controller.actions;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

public interface BlueprintSelectionListener {
    default void compartmentSelected(Compartment area) {}
}
