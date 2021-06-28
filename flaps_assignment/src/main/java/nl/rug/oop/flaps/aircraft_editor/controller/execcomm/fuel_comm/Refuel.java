package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.fuel_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.FuelTank;
/**
 * Refuel class - represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class Refuel extends Command {
    private final double level;
    private double oldLevel;

    public Refuel(Controller controller, FuelTank area, double level) {
        this.area = area;
        this.level = level;
        this.controller = controller;
    }

    /**
     * Perform fuel check and change the fuel level in the designated fuel tank;
     * Fetch log data to the user;
     */
    @Override
    public void execute() {
        FuelTank area = (FuelTank) this.area;
        Aircraft aircraft = controller.getAircraft();
        AircraftDataTracker dataTracker = controller.getDataTracker();
        this.oldLevel = aircraft.getFuelAmountForFuelTank(area);
        if (dataTracker.performFuelCheck(oldLevel, level)) {
            aircraft.setFuelAmountForFuelTank(area, level);
            controller.getAircraftLoadingModel().fireFuelUpdate();
            fetchLogData(true);
        } else {
            fetchLogData(false);
        }
    }

    @Override
    public void fetchLogData(boolean state) {
        String val = "Level: " + level + "; Fuel Tank: " + area.getName();
        if (state)
            controller.relayConfiguratorMsg(MessagesDb.FUEL_CONFIRM + val);
        else
            controller.relayConfiguratorMsg(MessagesDb.FUEL_ERROR + val);
    }

    /**
     * Resets the level in the fuelTank to the oldLevel;
     */
    @Override
    public void undo() {
        Aircraft aircraft = controller.getAircraft();
        aircraft.setFuelAmountForFuelTank((FuelTank) area, oldLevel);
        controller.getAircraftLoadingModel().fireFuelUpdate();
        controller.relayConfiguratorMsg(MessagesDb.UNDO_FUEL + " : " + level);
    }

    /**
     * Re-executes the default execute() operation method;
     */
    @Override
    public void redo() {
        execute();
        controller.relayConfiguratorMsg(MessagesDb.REDO_FUEL + " : " + level);
    }
}
