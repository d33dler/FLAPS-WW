package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;

import java.util.ArrayList;
import java.util.List;

/**
 * AircraftLoadingModel class - stores listeners for all loading typologies in lists and fires updates;
 */
@Getter
public class AircraftLoadingModel extends ChangeListenerModel implements
        CargoUnitsListener,
        FuelSupplyListener {
    @Setter
    private EditorCore editorCore;
    private List<CargoUnitsListener> cargoListenerList;
    private List<FuelSupplyListener> fuelListenerList;
    @Setter
    private AircraftDataTracker dataTracker;

    public AircraftLoadingModel() {
        cargoListenerList = new ArrayList<>();
        fuelListenerList = new ArrayList<>();
    }

    /**
     * Method overloading for different listener types
     */

    public void addListener(CargoUnitsListener listener) {
        this.cargoListenerList.add(listener);
    }

    public void addListener(FuelSupplyListener listener) {
        this.fuelListenerList.add(listener);
    }

    /**
     * fire updates for cargo loading changes listeners
     */
    public void fireCargoUpdate() {
        this.fireCargoUpdate(dataTracker);
        this.cargoListenerList.forEach(listener -> {
            listener.fireCargoUpdate(dataTracker);
        });
    }

    /**
     * fire updates for fuel loading changes listeners
     */
    public void fireFuelUpdate() {
        this.fireFuelUpdate(dataTracker);
        this.fuelListenerList.forEach(listener -> {
            listener.fireFuelUpdate(dataTracker);
        });
    }

    /**
     * fire all updates
     */
    public void fireAllUpdates() {
        fireFuelUpdate();
        fireCargoUpdate();
    }

    @Override
    public void fireFuelUpdate(AircraftDataTracker dataTracker) {
        CargoUnitsListener.super.fireCargoUpdate(dataTracker);
    }

    @Override
    public void fireCargoUpdate(AircraftDataTracker dataTracker) {
        CargoUnitsListener.super.fireCargoUpdate(dataTracker);
    }
}
