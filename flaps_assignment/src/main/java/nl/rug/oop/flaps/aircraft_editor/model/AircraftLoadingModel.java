package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import java.util.ArrayList;
import java.util.List;


@Getter
public class AircraftLoadingModel {
    private List<CargoUnitsListener> cargoListenerList;
    private List<FuelSupplyListener> fuelListenerList;
    @Setter
    private AircraftDataTracker dataTracker;

    public AircraftLoadingModel() {
        cargoListenerList = new ArrayList<>();
        fuelListenerList = new ArrayList<>();
    }

    public void addListener(CargoUnitsListener listener) {
        this.cargoListenerList.add(listener);
    }
    public void addListener(FuelSupplyListener listener) {
        this.fuelListenerList.add(listener);
    }

    public void fireCargoUpdate(Aircraft aircraft) {
        this.cargoListenerList.forEach(listener -> {
            listener.fireCargoTradeUpdate(aircraft, dataTracker);
        });
    }
    public void fireFuelUpdate(Aircraft aircraft) {
        this.fuelListenerList.forEach(listener -> {
            listener.fireFuelSupplyUpdate(aircraft, dataTracker);
        });
    }
}
