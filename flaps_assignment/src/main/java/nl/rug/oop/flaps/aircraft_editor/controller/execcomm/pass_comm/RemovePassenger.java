package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

public class RemovePassenger extends Command {

    private PassengerMediator mediator;
    private Passenger passenger;

    public RemovePassenger(Controller controller, Compartment cabin, Passenger passenger) {
        super(controller, cabin, controller.getLogger());
        this.passenger = passenger;
        this.mediator = controller.getPassengerMediator();
    }

    @Override
    public void execute() {
        controller.getAircraft()
                .getCabinPassengers()
                .get((Cabin) area)
                .remove(passenger);
        mediator.updateHashedPassenger(passenger,false);
        controller.getAircraftLoadingModel().firePassengerUpdate();
        fetchLogData(true);
    }

    @Override
    public void fetchLogData(boolean state) {
        controller.relayConfiguratorMsg(MessagesDb.REM_PASS_POS);
    }

    @Override
    public void undo() {
        controller.getAircraft()
                .getCabinPassengers()
                .get((Cabin) area)
                .add(passenger);
        mediator.updateHashedPassenger(passenger,true);
        controller.getAircraftLoadingModel().firePassengerUpdate();
        controller.relayConfiguratorMsg(MessagesDb.REM_PASS_UNDO);
    }

    @Override
    public void redo() {
        execute();
        controller.relayConfiguratorMsg(MessagesDb.REM_PASS_REDO);
    }
}
