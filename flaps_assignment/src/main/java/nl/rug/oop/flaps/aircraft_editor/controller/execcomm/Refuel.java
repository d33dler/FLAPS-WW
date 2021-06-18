package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.FuelTank;
/**
 * Refuel class - represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class Refuel extends Command {

    private FuelTank fuelTank;
    private double level, oldLevel;
    private Configurator configurator;

    public Refuel(Configurator configurator, FuelTank fuelTank, double level) {
        this.fuelTank = fuelTank;
        this.level = level;
        this.configurator = configurator;
    }

    /**
     * Perform fuel check and change the fuel level in the designated fuel tank;
     * Fetch log data to the user;
     */
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
            configurator.relayConfiguratorMsg(MessagesDb.FUEL_CONFIRM + val);
        else
            configurator.relayConfiguratorMsg(MessagesDb.FUEL_ERROR + val);
    }

    /**
     * Resets the level in the fuelTank to the oldLevel;
     */
    @Override
    public void undo() {
        Aircraft aircraft = configurator.getAircraft();
        aircraft.setFuelAmountForFuelTank(fuelTank, oldLevel);
        configurator.getAircraftLoadingModel().fireFuelUpdate();
        configurator.relayConfiguratorMsg(MessagesDb.UNDO_FUEL + " : " + level);
    }

    /**
     * Re-executes the default execute() operation method;
     */
    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(MessagesDb.REDO_FUEL + " : " + level);
    }
}
