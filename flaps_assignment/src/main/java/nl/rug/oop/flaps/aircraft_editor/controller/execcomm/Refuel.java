package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

public class Refuel extends Command {

    private FuelTank fuelTank;
    private double level, oldLevel;
    private Configurator configurator;

    public Refuel(Configurator configurator, FuelTank fuelTank, double level) {
        this.fuelTank = fuelTank;
        this.level = level;
        this.configurator = configurator;
    }

    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        AircraftDataTracker dataTracker = configurator.getDataTracker();
        this.oldLevel = aircraft.getFuelAmountForFuelTank(fuelTank);
        if (dataTracker.performFuelCheck(oldLevel, level)) {
            aircraft.setFuelAmountForFuelTank(fuelTank, level);
            configurator.getAircraftLoadingModel().fireFuelUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }

    @Override
    public void fetchLogData(boolean state) {
        String val = "Level: " + level + "; Fuel Tank: " + fuelTank.getName();
        if (state)
            configurator.relayConfiguratorMsg(LogMessagesStack.FUEL_CONFIRM + val);
        else
            configurator.relayConfiguratorMsg(LogMessagesStack.FUEL_ERROR + val);
    }

    @Override
    public void undo() {
        Aircraft aircraft = configurator.getAircraft();
        aircraft.setFuelAmountForFuelTank(fuelTank, oldLevel);
        configurator.getAircraftLoadingModel().fireFuelUpdate();
        configurator.relayConfiguratorMsg(LogMessagesStack.UNDO_FUEL + " : " + level);
    }

    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(LogMessagesStack.REDO_FUEL + " : " + level);
    }
}
