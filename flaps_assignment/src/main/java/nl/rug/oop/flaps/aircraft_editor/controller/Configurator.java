package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.CargoDatabase;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.aircraft_editor.view.LogPanel;
import nl.rug.oop.flaps.aircraft_editor.view.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import java.util.concurrent.ThreadLocalRandom;

public class Configurator {
    private Aircraft aircraft;
    private EditorCore editorCore;
    private AircraftDataTracker dataTracker;
    private BlueprintSelectionModel selectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private LogPanel logPanel;
    private transient static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());

    @Setter
    private String selectedId;

    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getSelectionModel();
        this.dataTracker = editorCore.getDataTracker();
        this.aircraftLoadingModel = editorCore.getAircraftLoadingModel();
        this.logPanel = editorCore.getEditorFrame().getLogPanel();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        String val = "Level: " + level + "; Fuel Tank: " + fuelTank.getName();
        if (dataTracker.performFuelCheck(aircraft.getFuelAmountForFuelTank(fuelTank),level )) {
            aircraft.setFuelAmountForFuelTank(fuelTank, level);
            logPanel.updateLog(LogMessagesStack.FUEL_CONFIRM + val);
            aircraftLoadingModel.fireFuelUpdate(aircraft);
        } else {
            logPanel.updateLog(LogMessagesStack.FUEL_ERROR + val);
        }
    }

    public void updateDatabaseTables(SettingsPanel settingsPanel) {
        editorCore.setCargoDatabase(new CargoDatabase(editorCore, settingsPanel));
    }


    public void unitAdded(Aircraft aircraft, CargoType cargoType, int amount) {
        CargoFreight carriage = new CargoFreight(cargoType, amount, amount * cargoType.getWeightPerUnit());
        carriage.setId(freightIdGen.generateId());
        updateHashedFreight(carriage);
        aircraft.getCargoAreaContents().get((CargoArea) selectionModel.getCompartment()).add(carriage);
        aircraftLoadingModel.fireCargoUpdate(aircraft);
    }

    public void unitRemoved(Aircraft aircraft, CargoFreight cargoFreight, int amount) {
        aircraft.getCargoAreaContents()
                .get((CargoArea) selectionModel.getCompartment())
                .remove(cargoFreight);
        updateAircraftCargo(aircraft, cargoFreight, amount);
        aircraftLoadingModel.fireCargoUpdate(aircraft);
    }

    public void allCargoRemove(Aircraft aircraft) {
        aircraft.getCargoAreaContents().get((CargoArea) selectionModel.getCompartment()).clear();
        aircraftLoadingModel.fireCargoUpdate(aircraft);
    }

    public void updateAircraftCargo(Aircraft aircraft, CargoFreight cargoFreight, int amount) {
        if (cargoFreight.getUnitCount() - amount > 0) {
            cargoFreight.setUnitCount(cargoFreight.getUnitCount() - amount);
            float weightPerUnit = cargoFreight.getCargoType().getWeightPerUnit();
            cargoFreight.setTotalWeight(cargoFreight.getUnitCount() * weightPerUnit);
            aircraft.getCargoAreaContents()
                    .get((CargoArea) selectionModel.getCompartment())
                    .add(cargoFreight);
            updateHashedFreight(cargoFreight);
        }
    }

    public void updateHashedFreight(CargoFreight carriage) {
        editorCore.getEditorFrame().getSettingsPanel().
                getCargoSettings().getFreightHashMap().
                put(carriage.getId(), carriage);
    }



}
