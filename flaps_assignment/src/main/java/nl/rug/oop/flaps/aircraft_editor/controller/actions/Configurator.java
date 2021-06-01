package nl.rug.oop.flaps.aircraft_editor.controller.actions;

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
import nl.rug.oop.flaps.simulation.model.cargo.CargoUnit;

import java.util.HashMap;
import java.util.Set;

public class Configurator implements CargoUnitsListener {
    Aircraft aircraft;
    EditorCore editorCore;
    BlueprintSelectionModel selectionModel;
    CargoManipulationModel cargoManipulationModel;

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

    @Override
    public void unitAdded(Aircraft aircraft, CargoUnit unit, Double amount) {
        CargoUnitsListener.super.unitAdded(aircraft, unit, amount);
        Set<CargoFreight> set = aircraft.getCargoAreaContents().get((CargoArea) selectionModel.getCompartment());
       // set.
    }

    @Override
    public void unitRemoved(Aircraft aircraft, CargoUnit unit, double amount) {
        CargoUnitsListener.super.unitRemoved(aircraft, unit, amount);

    }
}
