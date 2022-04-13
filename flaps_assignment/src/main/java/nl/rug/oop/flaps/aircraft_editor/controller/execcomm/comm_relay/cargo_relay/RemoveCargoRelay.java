package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;

/**
 * RemoveCargoRelay - relays the RemoveCargoUnit command
 */
public class RemoveCargoRelay extends SelectionCommand {
    private final CargoMediator mediator;

    public RemoveCargoRelay(Controller controller) {
        this.controller = controller;
        this.mediator = controller.getCargoMediator();
    }

    @Override
    public void confirmExec() {
        controller.unitRemoved(mediator.getSelectedFreight(), mediator.getQuantity());
    }
}
