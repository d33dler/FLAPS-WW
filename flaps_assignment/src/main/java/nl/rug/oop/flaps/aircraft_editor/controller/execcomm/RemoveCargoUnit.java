package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

import java.util.HashMap;

public class RemoveCargoUnit extends Command {
    private Configurator configurator;
    private CargoArea cargoArea;
    private CargoFreight cargoFreight;
    private HashMap<String, CargoFreight> freightSet = new HashMap<>();
    private int amount;

    public RemoveCargoUnit(Configurator configurator, CargoArea cargoArea, CargoFreight cargoFreight, int amount) {
        this.configurator = configurator;
        this.cargoArea = cargoArea;
        this.cargoFreight = cargoFreight;
        this.amount = amount;
        getData();
    }

    private void getData() {
        configurator.cloneSet(configurator.getAircraft().getCargoAreaContents(cargoArea), this.freightSet);
    }

    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        aircraft.getCargoAreaContents()
                .get(cargoArea)
                .remove(cargoFreight);
        updateAircraftCargo(aircraft, cargoFreight, amount);
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }

    public void updateAircraftCargo(Aircraft aircraft, CargoFreight cargoFreight, int amount) {
        if (cargoFreight.getUnitCount() - amount > 0) {
            cargoFreight.setUnitCount(cargoFreight.getUnitCount() - amount);
            float weightPerUnit = cargoFreight.getCargoType().getWeightPerUnit();
            cargoFreight.setTotalWeight(cargoFreight.getUnitCount() * weightPerUnit);
            aircraft.getCargoAreaContents()
                    .get(cargoArea)
                    .add(cargoFreight);
            configurator.updateHashedFreight(cargoFreight);
        }
    }

    @Override
    public void fetchLogData(boolean state) {
        configurator.relayConfiguratorMsg(LogMessagesStack.R_CARGO_POS);
    }

    @Override
    public void undo() {
        configurator.getAircraft().getCargoAreaContents(cargoArea).clear();
        freightSet.values().forEach(clonedFreight ->  {
            CargoFreight newOriginal = new CargoFreight();
            configurator.cloneAttributes(clonedFreight,newOriginal);
            configurator.getAircraft().getCargoAreaContents(cargoArea).add(newOriginal);
        });
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(LogMessagesStack.UNDO_REM_C);
    }

    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(LogMessagesStack.REDO_REM_C);
    }
}
