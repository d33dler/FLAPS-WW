package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

/**
 * Command abstract class - all command class objects extend from this class.
 */
public abstract class Command {
    protected Controller controller;
    protected Compartment area;
    @Getter
    protected Logger logger;

    public Command(Controller controller, Compartment area, Logger logger) {
        this.controller = controller;
        this.area = area;
        this.logger = logger;
    }


    public abstract void execute();
    public abstract void fetchLogData(boolean state);
    public abstract void undo();
    public abstract void redo();
}
