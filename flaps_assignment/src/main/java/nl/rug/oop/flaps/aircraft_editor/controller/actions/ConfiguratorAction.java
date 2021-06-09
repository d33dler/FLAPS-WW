package nl.rug.oop.flaps.aircraft_editor.controller.actions;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class ConfiguratorAction extends AbstractUndoableEdit {

    private Command command;

    public ConfiguratorAction(Command command) {
        super();
        this.command = command;
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        command.redo();
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        command.undo();
    }

    @Override
    public boolean canRedo() {
        return super.canRedo();
    }

    @Override
    public boolean canUndo() {
        return super.canUndo();
    }
}
