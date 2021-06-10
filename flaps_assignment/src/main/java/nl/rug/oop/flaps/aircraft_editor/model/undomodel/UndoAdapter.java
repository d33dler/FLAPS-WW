package nl.rug.oop.flaps.aircraft_editor.model.undomodel;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;

public class UndoAdapter implements UndoableEditListener {
    private UndoManager undoManager;

    public UndoAdapter(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    public void undoableEditHappened(UndoableEditEvent e) {
        UndoableEdit edit = e.getEdit();
        undoManager.addEdit(edit);
    }
}

