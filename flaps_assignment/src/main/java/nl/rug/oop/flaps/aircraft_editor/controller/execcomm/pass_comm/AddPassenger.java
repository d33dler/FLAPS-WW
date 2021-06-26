package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.LogPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;

public class AddPassenger extends Command {

    private final Passenger passenger;
    private final Compartment cabin;
    private Runnable runnable;

    public AddPassenger(Configurator config, Passenger passenger, Compartment cabin) {
        this.configurator = config;
        this.passenger = passenger;
        this.cabin = cabin;
    }

    @Override
    public LogPanel getLogPanel() {
        return super.getLogPanel();
    }

    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        AircraftDataTracker dataTracker = configurator.getDataTracker();
        AircraftLoadingModel aircraftLoadingModel = configurator.getAircraftLoadingModel();
        if (dataTracker.performPassengerCheck((Cabin) area, passenger.getWeight())) {
          addNewPassenger(aircraft);
            aircraftLoadingModel.fireCargoUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }
    private void addNewPassenger(Aircraft aircraft) {
        configurator.updateHashedPassenger(passenger);
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
