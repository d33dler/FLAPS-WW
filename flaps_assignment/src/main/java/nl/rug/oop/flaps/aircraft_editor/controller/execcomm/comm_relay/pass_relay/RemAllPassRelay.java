package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.pass_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;

public class RemAllPassRelay extends SelectionCommand {

    public RemAllPassRelay(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void confirmExec() {
        controller.allPassengerRemove();
    }
}
