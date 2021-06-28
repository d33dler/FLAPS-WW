package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.cargo_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

/**
 * RemoveCargoUnit class - represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class RemoveCargoUnit extends Command {
    private CargoFreight cargoFreight;
    private final int amount, oldCount;
    private CargoMediator mediator;
    public RemoveCargoUnit(Controller controller, CargoArea area, CargoFreight cargoFreight, int amount) {
        this.controller = controller;
        this.area = area;
        this.cargoFreight = cargoFreight;
        this.amount = amount;
        this.oldCount = cargoFreight.getUnitCount();
        this.mediator = controller.getCargoMediator();
    }

    /**
     * Executes the removal of a certain amount of units of a cargo type from a cargo freight;
     */
    @Override
    public void execute() {
        Aircraft aircraft = controller.getAircraft();
        updateAircraftCargo(aircraft, amount);
        controller.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }
    /**
     * Sets the new unit count if the new amount is > 0 , else the cargo is removed ;
     */
    public void updateAircraftCargo(Aircraft aircraft, int amount) {
        double newAmount = cargoFreight.getUnitCount() - amount;
        if (newAmount > 0) cargoFreight.setUnitCount((int) newAmount);
        else aircraft.getCargoAreaContents()
                .get(((CargoArea) area))
                .remove(cargoFreight);
        mediator.updateHashedFreight(cargoFreight, (int) newAmount);
    }

    @Override
    public void fetchLogData(boolean state) {
        controller.relayConfiguratorMsg(MessagesDb.R_CARGO_POS);
    }

    /**
     * undo has to either reset the unit count or insert the removed cargo freight;
     */
    @Override
    public void undo() {
        CargoArea area = (CargoArea) this.area;
        if (controller.getAircraft().getCargoAreaContents(area).contains(cargoFreight)) {
            for (CargoFreight freight : controller.getAircraft().getCargoAreaContents(area)) {
                if (freight.getId().equals(cargoFreight.getId())) {
                    freight.setUnitCount(oldCount);
                    break;
                }
            }
        } else {
            cargoFreight.setUnitCount(oldCount);
            controller.getAircraft().getCargoAreaContents(area).add(cargoFreight);
        }
        controller.getAircraftLoadingModel().fireCargoUpdate();
        controller.relayConfiguratorMsg(MessagesDb.UNDO_REM_C);
    }

    /**
     * redo re-executes the default execute() operation method
     */
    @Override
    public void redo() {
        execute();
        controller.relayConfiguratorMsg(MessagesDb.REDO_REM_C);
    }
}
