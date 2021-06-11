package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;

public class RemoveAllRelay extends SelectionCommand {
    Configurator configurator;

    public RemoveAllRelay(Configurator configurator) {
      this.configurator = configurator;
    }

    @Override
    public void confirmExec() {
        configurator.allCargoRemove();
    }
}
