package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import java.util.HashSet;
import java.util.Set;

@Getter
public class BlueprintSelectionModel {

    private Compartment compartment = null;
    //private FuelTank selectedFuelTank = null;

    private final Set<BlueprintSelectionListener> listenerSet;

    public BlueprintSelectionModel() {
        listenerSet = new HashSet<>();
    }

    public void addListener(BlueprintSelectionListener listener) {
        this.listenerSet.add(listener);
    }

    public void setSelectedCompartment(Compartment area) {
        this.compartment = area;
        this.listenerSet.forEach(listener -> {
            listener.cargoAreaSelected(area);
        });
    }
}
