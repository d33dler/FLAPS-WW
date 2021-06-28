package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

import java.util.HashMap;

/**
 * RemoveAllCargo class -  represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class RemoveAllCargo extends Command {
    private HashMap<String, CargoFreight> freightSet = new HashMap<>();

    public RemoveAllCargo(Controller controller, CargoArea area) {
        this.area = area;
        this.controller = controller;
        getData();
    }

    /**
     * Copy the entire cargo set for the undo process;
     */
    private void getData() {
        controller.copySet(controller.getAircraft().getCargoAreaContents((CargoArea) area), this.freightSet);
    }

    /**
     * Clears all members of the cargoFreight set in the designated cargo area;
     *
     */
    @Override
    public void execute() {
        controller.getAircraft().getCargoAreaContents().get(((CargoArea) area)).clear();
        controller.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }

    @Override
    public void fetchLogData(boolean state) {
        controller.relayConfiguratorMsg(MessagesDb.R_ALL_CARGO_POS);
    }

    /**
     * Restores the set by re-adding all removed members of the cargoFreight set;
     */
    @Override
    public void undo() {
        freightSet.values().forEach(clonedFreight ->  {
            controller.getAircraft().getCargoAreaContents(((CargoArea) area)).add(clonedFreight);
        });
        controller.getAircraftLoadingModel().fireCargoUpdate();
        controller.relayConfiguratorMsg(MessagesDb.UNDO_REMALL_C);
    }

    /**
     * redo re-executes the default execute() operation method
     */
    @Override
    public void redo() {
        execute();
        controller.relayConfiguratorMsg(MessagesDb.REDO_REMALL_C);
    }
}
