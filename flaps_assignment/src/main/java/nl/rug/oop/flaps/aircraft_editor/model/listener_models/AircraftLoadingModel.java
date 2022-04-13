package nl.rug.oop.flaps.aircraft_editor.model.listener_models;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.PassengerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * AircraftLoadingModel class - stores listeners for all loading typologies in lists and fires updates;
 */
@Getter
public class AircraftLoadingModel extends ChangeListenerModel implements
        CargoUnitsListener,
        FuelSupplyListener,
        PassengerListener {
    @Setter
    private EditorCore editorCore;
    private List<CargoUnitsListener> cargoListenerList;
    private List<FuelSupplyListener> fuelListenerList;
    private List<PassengerListener> passengerListeners;
    @Setter
    private AircraftDataTracker dataTracker;

    public AircraftLoadingModel() {
        cargoListenerList = new ArrayList<>();
        fuelListenerList = new ArrayList<>();
        passengerListeners = new ArrayList<>();
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

    public void addListener(PassengerListener listener) {
        this.passengerListeners.add(listener);
    }
    /**
     * fire updates for cargo loading changes listeners
     */
    public void fireCargoUpdate() {
        this.fireCargoUpdate(dataTracker);
        cargoListenerList.forEach(listener -> {
            listener.fireCargoUpdate(dataTracker);
        });
    }

    /**
     * fire updates for fuel loading changes listeners
     */
    public void fireFuelUpdate() {
        this.fireFuelUpdate(dataTracker);
        fuelListenerList.forEach(listener -> {
            listener.fireFuelUpdate(dataTracker);
        });
    }
    public void firePassengerUpdate() {
        this.firePassUpdate(dataTracker);
        passengerListeners.forEach(listener -> {
            listener.firePassUpdate(dataTracker);
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
        FuelSupplyListener.super.fireFuelUpdate(dataTracker);
    }

    @Override
    public void fireCargoUpdate(AircraftDataTracker dataTracker) {
        CargoUnitsListener.super.fireCargoUpdate(dataTracker);
    }

    @Override
    public void firePassUpdate(AircraftDataTracker dataTracker) {
        PassengerListener.super.firePassUpdate(dataTracker);
    }
}
