package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

import java.util.HashMap;

public class RemoveAllCargo extends Command {

    private CargoArea cargoArea;
    private Configurator configurator;
    private HashMap<String, CargoFreight> freightSet = new HashMap<>();

    public RemoveAllCargo(Configurator configurator, CargoArea cargoArea) {
        this.cargoArea = cargoArea;
        this.configurator = configurator;
        getData();
    }

    private void getData() {
        configurator.cloneSet(configurator.getAircraft().getCargoAreaContents(cargoArea), this.freightSet);
    }

    @Override
    public void execute() {
        configurator.getAircraft().getCargoAreaContents().get(cargoArea).clear();
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }

    @Override
    public void fetchLogData(boolean state) {
        configurator.relayConfiguratorMsg(LogMessagesStack.R_ALL_CARGO_POS);
    }

    @Override
    public void undo() {
        freightSet.values().forEach(clonedFreight ->  {
            CargoFreight newOriginal = new CargoFreight();
            configurator.cloneAttributes(clonedFreight,newOriginal);
            configurator.getAircraft().getCargoAreaContents(cargoArea).add(newOriginal);
        });
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(LogMessagesStack.UNDO_REMALL_C);
    }

    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(LogMessagesStack.REDO_REMALL_C);
    }
}
