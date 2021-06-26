package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;

/**
 * RemoveAllRelay - relays the RemoveAllCargo command
 */
public class RemoveAllRelay extends SelectionCommand {
    public RemoveAllRelay(Configurator configurator) {
      this.configurator = configurator;
    }

    @Override
    public void confirmExec() {
        configurator.allCargoRemove();
    }
}
