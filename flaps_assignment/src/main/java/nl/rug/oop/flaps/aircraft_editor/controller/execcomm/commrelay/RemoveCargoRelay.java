package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;

/**
 * RemoveCargoRelay - relays the RemoveCargoUnit command
 */
public class RemoveCargoRelay extends SelectionCommand {

    public RemoveCargoRelay(Configurator configurator) {
        this.configurator = configurator;
    }

    @Override
    public void confirmExec() {
        CargoFreight selectedFreight = configurator.getCargoTradeFrame().getSelectedFreight();
        int amount =  configurator.getCargoTradeFrame().getAmount();
        configurator.unitRemoved(selectedFreight, amount);
    }
}
