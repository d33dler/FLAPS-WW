package nl.rug.oop.flaps.aircraft_editor.controller.execcomm;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.LogPanel;

/**
 * Command abstract class - all command class objects extend from this class.
 */
public abstract class Command {
    @Getter
    private LogPanel logPanel;
    public abstract void execute();
    public abstract void fetchLogData(boolean state);
    public abstract void undo();
    public abstract void redo();
}
