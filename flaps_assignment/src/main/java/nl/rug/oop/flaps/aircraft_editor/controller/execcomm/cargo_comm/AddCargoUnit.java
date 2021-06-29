package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

/**
 * AddCargoUnit class -  represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class AddCargoUnit extends Command {
    private final CargoType cargoType;
    private final int amount;
    private CargoFreight cargoFreight;
    private CargoMediator mediator;
    public AddCargoUnit(Controller controller, CargoArea area, CargoType cargoType, int amount) {
        super(controller,area, controller.getLogger());
        this.cargoType = cargoType;
        this.amount = amount;
        this.mediator = controller.getCargoMediator();
    }

    /**
     * Performs cargoCheck and adds the new cargoFreight unit to the set of the designated cargo area;
     * Fetches log data to the user;
     */
    @Override
    public void execute() {
        Aircraft aircraft = controller.getAircraft();
        AircraftDataTracker dataTracker = controller.getDataTracker();
        AircraftLoadingModel aircraftLoadingModel = controller.getAircraftLoadingModel();
        float weight = amount * cargoType.getWeightPerUnit();
        if (dataTracker.performWeightCheck(area, weight)) {
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
                Controller.freightIdGen.generateId());
        mediator.updateHashedFreight(cargoFreight,amount);
        aircraft.getCargoAreaContents().get((CargoArea) area).add(cargoFreight);
    }

    public void fetchLogData(boolean state) {
        if (state)
            controller.relayConfiguratorMsg(MessagesDb.ADD_CARGO_POS);
        else
            controller.relayConfiguratorMsg(MessagesDb.ADD_CARGO_NEG);
    }

    /**
     * Removes the cargoFreight from the set;
     */
    @Override
    public void undo() {
        controller.getAircraft()
                .getCargoAreaContents((CargoArea) area)
                .remove(cargoFreight);
        controller.getAircraftLoadingModel().fireCargoUpdate();
        mediator.updateHashedFreight(cargoFreight,-1);
        controller.relayConfiguratorMsg(MessagesDb.UNDO_ADD_C);
    }

    /**
     * Re-adds the cargoFreight, but doesn't create a new instance.
     */
    @Override
    public void redo() {
        controller.getAircraft()
                .getCargoAreaContents((CargoArea) area)
                .add(cargoFreight);
        controller.getAircraftLoadingModel().fireCargoUpdate();
        controller.relayConfiguratorMsg(MessagesDb.REDO_ADD_C);
    }
}
