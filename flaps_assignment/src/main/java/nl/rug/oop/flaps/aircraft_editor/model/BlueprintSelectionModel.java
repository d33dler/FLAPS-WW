package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import java.util.HashSet;
import java.util.Set;

@Getter
public class BlueprintSelectionModel {

    private CargoArea selectedCargoArea = null;
    private FuelTank selectedFuelTank = null;

    private final Set<BlueprintSelectionListener> listenerSet;

    public BlueprintSelectionModel() {
        listenerSet = new HashSet<>();
    }

    public void addListener(BlueprintSelectionListener listener) {
        this.listenerSet.add(listener);
    }

    public void setSelectedFuelTank(FuelTank fuelTank) {
        this.selectedFuelTank = fuelTank;
        this.selectedCargoArea = null;
        this.listenerSet.forEach(listener -> {
            listener.fuelTankSelected(fuelTank);
            listener.cargoAreaSelected(null);
        });
    }

    public void setSelectedCargoArea(CargoArea cargoArea) {
        this.selectedCargoArea = cargoArea;
        this.selectedFuelTank = null;
        this.listenerSet.forEach(listener -> {
            listener.cargoAreaSelected(cargoArea);
            listener.fuelTankSelected(null);
        });
    }
}
