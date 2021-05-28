package nl.rug.oop.flaps.aircraft_editor.model;

import nl.rug.oop.flaps.aircraft_editor.view.LogPanel;
import nl.rug.oop.flaps.aircraft_editor.view.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

public class Configurator {
    Aircraft aircraft;
    EditorCore editorCore;

    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        aircraft.getFuelTankFillStatuses().putIfAbsent(fuelTank,level);
        editorCore.getEditorFrame().getLogPanel().updateLog(LogPanel.FUEL_CONFIRM);
    }
    public void updateDatabaseTables(SettingsPanel settingsPanel) {
        editorCore.setCargoDatabase(new CargoDatabase(editorCore, settingsPanel));
    }
}
