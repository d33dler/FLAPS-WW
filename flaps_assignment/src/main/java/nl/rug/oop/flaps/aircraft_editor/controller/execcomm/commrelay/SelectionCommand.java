package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.commrelay;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;

public abstract class SelectionCommand {
    @Getter
    @Setter
    protected Configurator configurator;
    public abstract void confirmExec();
}
