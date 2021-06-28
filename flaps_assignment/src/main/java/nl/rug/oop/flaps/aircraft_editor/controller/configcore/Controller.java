package nl.rug.oop.flaps.aircraft_editor.controller.configcore;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.Randomizers;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.ConfiguratorAction;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm.AddCargoUnit;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm.RemoveAllCargo;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm.RemoveCargoUnit;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.fuel_comm.Refuel;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm.AddPassenger;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.model.undomodel.UndoRedoManager;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.FuelTank;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controller class - creates the command objects and executes their methods,
 * also stores the commands in the UndoManagers edit archive.
 */
@Getter
public class Controller {
    private Aircraft aircraft;
    private EditorCore editorCore;
    private AircraftDataTracker dataTracker;
    private BlueprintSelectionModel selectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private UndoRedoManager undoRedoManager;
    @Setter
    private Logger logger;
    @Setter
    private CargoMediator cargoMediator;
    @Setter
    private PassengerMediator passengerMediator;
    public static final Randomizers freightIdGen = new Randomizers(5, ThreadLocalRandom.current());
    private List<Command> commandList;
    @Setter
    private SelectionCommand selectionCommand;


    public Controller(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.selectionModel = editorCore.getBpSelectionModel();
        this.dataTracker = editorCore.getDataTracker();
        this.aircraftLoadingModel = editorCore.getAircraftLoadingModel();
        this.undoRedoManager = editorCore.getUndoRedoManager();
        this.commandList = undoRedoManager.getCommandsList();
    }

    public void updateFuelStatus(FuelTank fuelTank, double level) {
        addExecuteLastCommand(new Refuel(this, fuelTank, level));
    }

    public void unitAdded(CargoType cargoType, int amount) {
        addExecuteLastCommand(new AddCargoUnit(this, (CargoArea) fetchArea(), cargoType, amount));
    }

    public void unitRemoved(CargoFreight cargoFreight, int amount) {
        addExecuteLastCommand(new RemoveCargoUnit(this, (CargoArea) fetchArea(), cargoFreight, amount));
    }

    public void allCargoRemove() {
        addExecuteLastCommand(new RemoveAllCargo(this, (CargoArea) fetchArea()));
    }

    public void passengerAdded(List<JTextField> set, PassengerType type) {
        addExecuteLastCommand(new AddPassenger(this, fetchArea(),set,type));

    }

    public void passengerRemoved() {

    }

    public void allPassengerRemove() {

    }



    public Compartment fetchArea() {
        return selectionModel.getFocusCompartment();
    }


    private void addExecuteLastCommand(Command command) {
        commandList.add(command);
        undoRedoManager.mementoConfig(new ConfiguratorAction(lastCommand()));
        lastCommand().execute();
    }

    public void delegateCommand(ControlSolicitor solicitor) {
        solicitor.performPriorUpdates();
        if (selectionCommand != null) selectionCommand.confirmExec();
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
        logger.updateLog(MSG);
    }
}
