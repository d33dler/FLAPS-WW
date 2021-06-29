package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

import java.util.HashSet;
import java.util.Set;

public class RemoveAllPassengers extends Command {

    private Set<Passenger> passengerList;
    private PassengerMediator mediator;
    private Aircraft aircraft;

    public RemoveAllPassengers(Controller controller, Cabin fetchArea) {
        super(controller, fetchArea, controller.getLogger());
        this.mediator = controller.getPassengerMediator();
    }

    @Override
    public void execute() {
        aircraft = controller.getAircraft();
        passengerList = aircraft.getCabinPassengers().get((Cabin) area);
        aircraft.getCabinPassengers().put((Cabin) area, new HashSet<>());
        mediator.setPassengerSet(aircraft.getCabinPassengers().get((Cabin) area));
        controller.getAircraftLoadingModel().firePassengerUpdate();
        fetchLogData(true);
    }

    @Override
    public void fetchLogData(boolean state) {
    }

    @Override
    public void undo() {
        aircraft.getCabinPassengers().put((Cabin) area, passengerList);
        mediator.setPassengerSet(passengerList);
        controller.getAircraftLoadingModel().firePassengerUpdate();
        controller.relayConfiguratorMsg(MessagesDb.REMALL_PASS_UNDO);
    }

    @Override
    public void redo() {
        execute();
        controller.relayConfiguratorMsg(MessagesDb.REMALL_PASS_REDO);
    }
}
