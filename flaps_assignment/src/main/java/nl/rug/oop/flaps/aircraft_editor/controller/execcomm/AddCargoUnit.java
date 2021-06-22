package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

/**
 * AddCargoUnit class -  represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class AddCargoUnit extends Command {
    private final Configurator configurator;
    private CargoArea cargoArea;
    private final CargoType cargoType;
    private final int amount;
    private CargoFreight cargoFreight;

    public AddCargoUnit(Configurator configurator, CargoArea cargoArea, CargoType cargoType, int amount) {
        this.cargoArea = cargoArea;
        this.cargoType = cargoType;
        this.amount = amount;
        this.configurator = configurator;
    }

    /**
     * Performs cargoCheck and adds the new cargoFreight unit to the set of the designated cargo area;
     * Fetches log data to the user;
     */
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

    /**
     * Creates new cargoFreight and sets the unit count
     * Stores the cargoFreight here for future undo operations
     */
    private void generateAddCarriage(Aircraft aircraft) {
        this.cargoFreight = new CargoFreight(cargoType, amount,
                amount * cargoType.getWeightPerUnit(),
                Configurator.freightIdGen.generateId());
        configurator.updateHashedFreight(cargoFreight);
        aircraft.getCargoAreaContents().get(cargoArea).add(cargoFreight);
    }

    public void fetchLogData(boolean state) {
        if (state)
            configurator.relayConfiguratorMsg(MessagesDb.ADD_CARGO_POS);
        else
            configurator.relayConfiguratorMsg(MessagesDb.ADD_CARGO_NEG);
    }

    /**
     * Removes the cargoFreight from the set;
     */
    @Override
    public void undo() {
        configurator.getAircraft()
                .getCargoAreaContents(cargoArea)
                .remove(cargoFreight);
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(MessagesDb.UNDO_ADD_C);
    }

    /**
     * Re-adds the cargoFreight, but doesn't create a new instance.
     */
    @Override
    public void redo() {
        configurator.getAircraft()
                .getCargoAreaContents(cargoArea)
                .add(cargoFreight);
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(MessagesDb.REDO_ADD_C);
    }
}
