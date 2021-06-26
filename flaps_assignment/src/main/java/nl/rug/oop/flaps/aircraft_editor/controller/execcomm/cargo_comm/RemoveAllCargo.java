package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
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

    public RemoveAllCargo(Configurator configurator, CargoArea area) {
        this.area = area;
        this.configurator = configurator;
        getData();
    }

    /**
     * Copy the entire cargo set for the undo process;
     */
    private void getData() {
        configurator.copySet(configurator.getAircraft().getCargoAreaContents((CargoArea) area), this.freightSet);
    }

    /**
     * Clears all members of the cargoFreight set in the designated cargo area;
     *
     */
    @Override
    public void execute() {
        configurator.getAircraft().getCargoAreaContents().get(((CargoArea) area)).clear();
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }

    @Override
    public void fetchLogData(boolean state) {
        configurator.relayConfiguratorMsg(MessagesDb.R_ALL_CARGO_POS);
    }

    /**
     * Restores the set by re-adding all removed members of the cargoFreight set;
     */
    @Override
    public void undo() {
        freightSet.values().forEach(clonedFreight ->  {
            configurator.getAircraft().getCargoAreaContents(((CargoArea) area)).add(clonedFreight);
        });
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(MessagesDb.UNDO_REMALL_C);
    }

    /**
     * redo re-executes the default execute() operation method
     */
    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(MessagesDb.REDO_REMALL_C);
    }
}
