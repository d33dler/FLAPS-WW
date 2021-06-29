package nl.rug.oop.flaps.aircraft_editor.controller.execcomm.pass_comm;

import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;

public class RemoveAllPassengers extends Command {


    public RemoveAllPassengers(Controller controller, Cabin fetchArea) {
        super(controller,fetchArea, controller.getLogger());
    }

    @Override
    public void execute() {

    }

    @Override
    public void fetchLogData(boolean state) {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
