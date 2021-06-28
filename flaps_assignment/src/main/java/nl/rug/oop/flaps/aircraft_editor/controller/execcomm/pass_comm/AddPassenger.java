package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;
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
        this.controller = controller;
        this.mediator = controller.getPassengerMediator();
        this.type = type;
        this.area = cabin;
        createPassenger(blankDoc);
    }


    @Override
    public void execute() {
        Aircraft aircraft = controller.getAircraft();
        AircraftDataTracker dataTracker = controller.getDataTracker();
        AircraftLoadingModel aircraftLoadingModel = controller.getAircraftLoadingModel();
        if (dataTracker.performPassengerCheck((Cabin) area, Double.parseDouble(passenger.getWeight()))) {
            addNewPassenger(aircraft);
            aircraftLoadingModel.fireCargoUpdate();
            controller.getAircraftLoadingModel().firePassengerUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }

    private void addNewPassenger(Aircraft aircraft) {
        mediator.updateHashedPassenger(passenger);
        aircraft.getCabinPassengers().get((Cabin) area).add(passenger);
    }

    private void createPassenger(List<JTextField> blankDoc) {
        this.passenger = new Passenger.Builder(mediator).readBlanks(blankDoc, type.getId(), type);
    }

    @Override
    public void fetchLogData(boolean state) {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
