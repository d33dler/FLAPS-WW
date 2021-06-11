package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;

public class RemoveCargoRelay extends SelectionCommand {

    public RemoveCargoRelay(Configurator configurator) {
        this.configurator = configurator;
    }

    @Override
    public void confirmExec() {
        configurator.allCargoRemove();
    }
}
