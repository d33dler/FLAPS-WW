package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import java.util.HashMap;


public class AddCargoUnit extends Command {
    private final Configurator configurator;
    private CargoArea cargoArea;
    private final CargoType cargoType;
    private final int amount;
    private HashMap<String, CargoFreight> freightSet = new HashMap<>();

    public AddCargoUnit(Configurator configurator, CargoArea cargoArea, CargoType cargoType, int amount) {
        this.cargoArea = cargoArea;
        this.cargoType = cargoType;
        this.amount = amount;
        this.configurator = configurator;
        getData();
    }

    private void getData() {
        configurator.cloneSet(configurator.getAircraft().getCargoAreaContents(cargoArea), this.freightSet);
    }

    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        AircraftDataTracker dataTracker = configurator.getDataTracker();
        AircraftLoadingModel aircraftLoadingModel = configurator.getAircraftLoadingModel();
        float weight = amount * cargoType.getWeightPerUnit();
        if (dataTracker.performCargoCheck(weight)) {
            generateAddCarriage(aircraft);
            aircraftLoadingModel.fireCargoUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }

    private void generateAddCarriage(Aircraft aircraft) {
        CargoFreight carriage = new CargoFreight(cargoType, amount,
                amount * cargoType.getWeightPerUnit(),
                Configurator.freightIdGen.generateId());
        configurator.updateHashedFreight(carriage);
        aircraft.getCargoAreaContents().get(cargoArea).add(carriage);
    }

    public void fetchLogData(boolean state) {
        if (state)
            configurator.relayConfiguratorMsg(LogMessagesStack.ADD_CARGO_POS);
        else
            configurator.relayConfiguratorMsg(LogMessagesStack.ADD_CARGO_NEG);
    }

    @Override
    public void undo() {
        configurator.getAircraft()
                .getCargoAreaContents(cargoArea)
                .removeIf(cargoFreight ->
                        !freightSet.containsKey(cargoFreight.getId()));
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(LogMessagesStack.UNDO_ADD_C);
    }

    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(LogMessagesStack.REDO_ADD_C);
    }
}
