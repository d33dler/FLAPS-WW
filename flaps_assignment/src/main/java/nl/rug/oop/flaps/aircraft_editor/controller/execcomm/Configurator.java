package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.Randomizers;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.ConfiguratorAction;
import nl.rug.oop.flaps.aircraft_editor.model.*;
import nl.rug.oop.flaps.aircraft_editor.view.LogPanel;
import nl.rug.oop.flaps.aircraft_editor.view.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.loaders.DataHolder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

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
    private DataHolder holder = new DataHolder();
    public static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());
    private List<Command> commandList;


    public Configurator(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getSelectionModel();
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
                getCargoSettings().getFreightHashMap().
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

    public void pasteCargoFreight(Set<CargoFreight> dynamicSet, HashMap<String, CargoFreight> cloneSet){

    }

    public void cloneSet(Set<CargoFreight> dynamicSet, HashMap<String, CargoFreight> cloneSet) {
        dynamicSet.forEach(originalFreight -> {
            CargoFreight clone = new CargoFreight();
            cloneAttributes(originalFreight, clone);
            cloneSet.put(clone.getId(), clone);
        });
    }

    public void cloneAttributes(CargoFreight original, CargoFreight clone) {
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                field.set(clone, field.get(original));
            } catch (IllegalAccessException e) {
                System.out.println("Could not retrieve one of the freight obj fields.");
            }
        }
    }
    public void relayConfiguratorMsg(String MSG) {
        logPanel.updateLog(MSG);
    }
}
