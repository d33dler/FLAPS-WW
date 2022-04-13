package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.cargo_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay.SelectionCommand;

/**
 * RemoveAllRelay - relays the RemoveAllCargo command
 */
public class RemAllCargoRelay extends SelectionCommand {
    public RemAllCargoRelay(Controller controller) {
      this.controller = controller;
    }

    @Override
    public void confirmExec() {
        controller.allCargoRemove();
    }
}
