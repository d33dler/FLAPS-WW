package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.Randomizers;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.CargoDatabase;
import nl.rug.oop.flaps.aircraft_editor.model.CargoManipulationModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.CargoSettings;
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
    private BlueprintSelectionModel selectionModel;
    private CargoManipulationModel cargoManipulationModel;
    private transient static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());

    @Setter
    private String selectedId;

    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getSelectionModel();
        this.cargoManipulationModel = editorCore.getCargoManipulationModel();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        aircraft.getFuelTankFillStatuses().putIfAbsent(fuelTank, level);
        String val = "(" + level + ")";
        editorCore.getEditorFrame().getLogPanel().updateLog(LogPanel.FUEL_CONFIRM + val);
    }

    public void updateDatabaseTables(SettingsPanel settingsPanel) {
        editorCore.setCargoDatabase(new CargoDatabase(editorCore, settingsPanel));
    }


    public void unitAdded(Aircraft aircraft, CargoType cargoType, int amount) {
        CargoFreight carriage = new CargoFreight(cargoType, amount, amount * cargoType.getWeightPerUnit());
        carriage.setId(freightIdGen.generateId());
        editorCore.getEditorFrame().getSettingsPanel().
                getCargoSettings().getFreightHashMap().
                put(carriage.getId(),carriage);
        aircraft.getCargoAreaContents().get((CargoArea) selectionModel.getCompartment()).add(carriage);
        editorCore.getCargoManipulationModel().fireUpdate(aircraft, cargoType, amount);
    }

    public void unitRemoved(Aircraft aircraft, CargoType cargoType, int amount) {
        CargoFreight cargoFreight = editorCore.getEditorFrame()
                .getSettingsPanel().getCargoSettings().getSelectedFreight();
        aircraft.getCargoAreaContents()
            .get((CargoArea) selectionModel.getCompartment())
            .remove(cargoFreight);
        cargoManipulationModel.fireUpdate(aircraft,cargoFreight,amount);
    }

    public void allCargoRemove(Aircraft aircraft) {
        //  cargoManipulationModel.removeUnit(aircraft, cargoType, amount);
    }

}
