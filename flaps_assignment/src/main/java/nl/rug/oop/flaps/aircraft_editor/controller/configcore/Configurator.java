package nl.rug.oop.flaps.aircraft_editor.controller.configcore;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.Randomizers;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.ConfiguratorAction;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.*;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.CargoDatabase;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.undomodel.UndoRedoManager;
import nl.rug.oop.flaps.aircraft_editor.view.cargoeditor.CargoTradeFrame;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.LogPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.FuelTank;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Configurator class - creates the command objects and executes their methods,
 * also stores the commands in the UndoManagers edit archive.
 */
@Getter
public class Configurator {
    private Aircraft aircraft;
    private EditorCore editorCore;
    private AircraftDataTracker dataTracker;
    private BlueprintSelectionModel selectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private UndoRedoManager undoRedoManager;
    @Setter
    private LogPanel logPanel;
    @Setter
    private CargoTradeFrame cargoTradeFrame;
    public static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());
    private List<Command> commandList;


    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getBpSelectionModel();
        this.dataTracker = editorCore.getDataTracker();
        this.aircraftLoadingModel = editorCore.getAircraftLoadingModel();
        this.undoRedoManager = editorCore.getUndoRedoManager();
        this.commandList = undoRedoManager.getCommandsList();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        commandList.add(new Refuel(this, fuelTank, level));
        addExecuteLastCommand();
    }

    public void updateDatabaseTables(SettingsPanel settingsPanel) {
        editorCore.setCargoDatabase(new CargoDatabase(editorCore, settingsPanel));
    }


    public void unitAdded(CargoType cargoType, int amount) {
        commandList.add(new AddCargoUnit(this, (CargoArea) fetchArea(), cargoType, amount));
        addExecuteLastCommand();
    }

    public void unitRemoved(CargoFreight cargoFreight, int amount) {
        commandList.add(new RemoveCargoUnit(this, (CargoArea) fetchArea(), cargoFreight, amount));
        addExecuteLastCommand();
    }

    public void allCargoRemove() {
        commandList.add(new RemoveAllCargo(this, (CargoArea) fetchArea()));
        addExecuteLastCommand();
    }

    public void updateHashedFreight(CargoFreight carriage) {
        editorCore.getEditorFrame().getSettingsPanel().
                getCargoTradeFrame().getFreightHashMap().
                put(carriage.getId(), carriage);
    }

    public Compartment fetchArea() {
        return selectionModel.getCompartment();
    }


    private void addExecuteLastCommand() {
        undoRedoManager.mementoConfig(new ConfiguratorAction(lastCommand()));
        lastCommand().execute();
    }

    private Command lastCommand() {
        return commandList.get((commandList.size() - 1));
    }

    public void copySet(Set<CargoFreight> dynamicSet, HashMap<String, CargoFreight> cloneSet) {
        dynamicSet.forEach(originalFreight -> {
            cloneSet.put(originalFreight.getId(), originalFreight);
        });
    }

    public void relayConfiguratorMsg(String MSG) {
        logPanel.updateLog(MSG);
    }
}
