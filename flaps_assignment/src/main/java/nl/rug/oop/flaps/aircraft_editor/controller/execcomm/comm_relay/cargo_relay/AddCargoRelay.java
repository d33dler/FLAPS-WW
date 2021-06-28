package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.CargoMediator;

/**
 * AddCargoRelay - relays the AddCargoUnit command
 */
public class AddCargoRelay extends SelectionCommand {
    private final CargoMediator mediator;

    public AddCargoRelay(Controller controller) {
        this.controller = controller;
        this.mediator = controller.getCargoMediator();
    }

    @Override
    public void confirmExec() {
        controller.unitAdded(mediator.getSelectedType(), mediator.getQuantity());
    }
}
