package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

/**
 * RemoveCargoUnit class - represent a command object (extending from abstract class Command) and implementing
 * all its abstract methods.
 */
public class RemoveCargoUnit extends Command {
    private Configurator configurator;
    private CargoArea cargoArea;
    private CargoFreight cargoFreight;
    private int amount, oldCount;

    public RemoveCargoUnit(Configurator configurator, CargoArea cargoArea, CargoFreight cargoFreight, int amount) {
        this.configurator = configurator;
        this.cargoArea = cargoArea;
        this.cargoFreight = cargoFreight;
        this.amount = amount;
        this.oldCount = cargoFreight.getUnitCount();
    }

    /**
     * Executes the removal of a certain amount of units of a cargo type from a cargo freight;
     */
    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        updateAircraftCargo(aircraft, amount);
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }
    /**
     * Sets the new unit count if the new amount is > 0 , else the cargo is removed ;
     */
    public void updateAircraftCargo(Aircraft aircraft, int amount) {
        double newAmount = cargoFreight.getUnitCount() - amount;
        if (newAmount > 0) {
            cargoFreight.setUnitCount((int) newAmount);
            configurator.updateHashedFreight(cargoFreight);
        } else {
            aircraft.getCargoAreaContents()
                    .get(cargoArea)
                    .remove(cargoFreight);
        }
    }

    @Override
    public void fetchLogData(boolean state) {
        configurator.relayConfiguratorMsg(MessagesDb.R_CARGO_POS);
    }

    /**
     * undo has to either reset the unit count or insert the removed cargo freight;
     */
    @Override
    public void undo() { //TODO terrible performance (because of Set<Cargofreight> needs improvement
        if (configurator.getAircraft().getCargoAreaContents(cargoArea).contains(cargoFreight)) {
            for (CargoFreight freight : configurator.getAircraft().getCargoAreaContents(cargoArea)) {
                if (freight.getId().equals(cargoFreight.getId())) {
                    freight.setUnitCount(oldCount);
                    break;
                }
            }
        } else {
            cargoFreight.setUnitCount(oldCount);
            configurator.getAircraft().getCargoAreaContents(cargoArea).add(cargoFreight);
        }
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(MessagesDb.UNDO_REM_C);
    }

    /**
     * redo re-executes the default execute() operation method
     */
    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(MessagesDb.REDO_REM_C);
    }
}
