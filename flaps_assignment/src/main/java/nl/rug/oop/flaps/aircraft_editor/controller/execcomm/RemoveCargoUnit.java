package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import nl.rug.oop.flaps.aircraft_editor.view.LogMessagesStack;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

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


    @Override
    public void execute() {
        Aircraft aircraft = configurator.getAircraft();
        updateAircraftCargo(aircraft, amount);
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        fetchLogData(true);
    }

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
        configurator.relayConfiguratorMsg(LogMessagesStack.R_CARGO_POS);
    }

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
            CargoFreight newOriginal = new CargoFreight();
            configurator.cloneAttributes(cargoFreight, newOriginal);
            cargoFreight = newOriginal;
            configurator.getAircraft().getCargoAreaContents(cargoArea).add(newOriginal);
        }
        configurator.getAircraftLoadingModel().fireCargoUpdate();
        configurator.relayConfiguratorMsg(LogMessagesStack.UNDO_REM_C);
    }

    @Override
    public void redo() {
        execute();
        configurator.relayConfiguratorMsg(LogMessagesStack.REDO_REM_C);
    }
}
