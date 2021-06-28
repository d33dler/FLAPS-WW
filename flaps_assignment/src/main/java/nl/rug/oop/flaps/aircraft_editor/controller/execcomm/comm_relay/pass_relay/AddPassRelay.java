package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;
import nl.rug.oop.flaps.aircraft_editor.model.mediators.PassengerMediator;

public class AddPassRelay extends SelectionCommand {

    private PassengerMediator mediator;

    public AddPassRelay(Controller controller) {
        this.controller = controller;
        this.mediator = controller.getPassengerMediator();
    }

    @Override
    public void confirmExec() {
        controller.passengerAdded(mediator.getBlankSet(),mediator.getSelectedType());
    }
}
