package nl.rug.oop.flaps.aircraft_editor.controller;

import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.CargoDatabase;
import nl.rug.oop.flaps.aircraft_editor.model.CargoManipulationModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
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
    private MassTracker massTracker;
    private BlueprintSelectionModel selectionModel;
    private CargoManipulationModel cargoManipulationModel;
    private LogPanel logPanel;
    private transient static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());

    @Setter
    private String selectedId;

    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getSelectionModel();
        this.massTracker = editorCore.getMassTracker();
        this.cargoManipulationModel = editorCore.getCargoManipulationModel();
        this.logPanel = editorCore.getEditorFrame().getLogPanel();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        String val = "Level: " + level + "; Fuel Tank: " + fuelTank.getName();
        if (massTracker.performCheck((float) (level * aircraft.getType().getFuelType().getDensity()))) {
            aircraft.setFuelAmountForFuelTank(fuelTank, level);
            logPanel.updateLog(LogPanel.FUEL_CONFIRM + val);
        } else {
            logPanel.updateLog(LogPanel.FUEL_ERROR + val);
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
        cargoManipulationModel.fireUpdate(aircraft, cargoType, amount);
    }

    public void unitRemoved(Aircraft aircraft, CargoFreight cargoFreight, int amount) {
        aircraft.getCargoAreaContents()
                .get((CargoArea) selectionModel.getCompartment())
                .remove(cargoFreight);
        updateAircraftCargo(aircraft, cargoFreight, amount);
        cargoManipulationModel.fireUpdate(aircraft, cargoFreight, amount);
    }

    public void allCargoRemove(Aircraft aircraft) {
        aircraft.getCargoAreaContents().get((CargoArea) selectionModel.getCompartment()).clear();
        cargoManipulationModel.fireUpdate(aircraft, (CargoFreight) null, 0);
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

    public void setCargoAreaLoad() {
        aircraft.getCargoAreaContents((CargoArea) selectionModel.getCompartment()).forEach(cargoFreight ->
                massTracker.setAreaMass((float) (massTracker.getAreaMass() + cargoFreight.getTotalWeight())));
    }

    public void setFuelTankLoad() {
        massTracker.setAreaMass
                (Float.parseFloat
                        (String.valueOf(aircraft.getFuelAmountForFuelTank((FuelTank) selectionModel.getCompartment()))));
    }
}
