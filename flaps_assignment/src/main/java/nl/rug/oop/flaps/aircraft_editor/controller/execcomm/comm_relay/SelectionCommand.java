package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.comm_relay;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;

/**
 * SelectionCommand abstract class - all command relay objects extend from this class and override the confirmExec()
 * method for a specific <? extends Command> class object. It can be used to execute compound commands
 * (formed from a series of simple commands)
 * (also, for compound commands with non-deterministic outcomes where redo - undo might insert wrong data)
 * This contributes to scalability;
 */
public abstract class SelectionCommand {
    @Getter
    @Setter
    protected Configurator configurator;
    public abstract void confirmExec();
}
