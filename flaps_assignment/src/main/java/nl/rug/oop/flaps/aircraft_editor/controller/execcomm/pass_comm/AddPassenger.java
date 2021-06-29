package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import java.util.List;

public class AddPassenger extends Command {

    private Passenger passenger;
    private PassengerMediator mediator;
    private PassengerType type;

    public AddPassenger(Controller controller, Compartment cabin, List<JTextField> blankDoc, PassengerType type) {
        super(controller, cabin, controller.getLogger());
        this.mediator = controller.getPassengerMediator();
        this.type = type;
        createPassenger(blankDoc);
    }


    @Override
    public void execute() {
        Aircraft aircraft = controller.getAircraft();
        AircraftDataTracker dataTracker = controller.getDataTracker();
        if (dataTracker.performPassengerCheck((Cabin) area, Double.parseDouble(passenger.getWeight()))) {
            addNewPassenger(aircraft);
            controller.getAircraftLoadingModel().firePassengerUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }

    private void addNewPassenger(Aircraft aircraft) {
        mediator.updateHashedPassenger(passenger, true);
        aircraft.getCabinPassengers().get((Cabin) area).add(passenger);
    }

    private void createPassenger(List<JTextField> blankDoc) {
        this.passenger = new Passenger.Builder(mediator).readBlanks(blankDoc, type.getId(), type);
    }

    @Override
    public void fetchLogData(boolean state) {
        if (state) {
            controller.relayConfiguratorMsg(MessagesDb.ADD_PASS_POS);
        } else {
            controller.relayConfiguratorMsg(MessagesDb.ADD_PASS_NEG);
        }
    }

    @Override
    public void undo() {
        controller.getAircraft()
                .getCabinPassengers()
                .get((Cabin) area)
                .remove(passenger);
        controller.getAircraftLoadingModel().firePassengerUpdate();
        controller.relayConfiguratorMsg(MessagesDb.ADD_PASS_UNDO);
    }

    @Override
    public void redo() {
        controller.getAircraft()
                .getCabinPassengers()
                .get((Cabin) area)
                .add(passenger);
        controller.getAircraftLoadingModel().firePassengerUpdate();
        controller.relayConfiguratorMsg(MessagesDb.ADD_PASS_REDO);
    }
}
