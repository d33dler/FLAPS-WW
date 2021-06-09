package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Command;
import nl.rug.oop.flaps.aircraft_editor.controller.execcomm.Configurator;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import javax.swing.undo.UndoableEditSupport;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UndoRedoManager {
    private final EditorCore editorCore;
    private Configurator configurator;
    private final UndoManager undoManager;
    private final UndoAdapter undoAdapter;
    protected UndoableEditSupport undoSupport;
    protected List<Command> commandsList = new ArrayList<>();

    public UndoRedoManager(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.undoManager = new UndoManager();
        this.undoAdapter = new UndoAdapter(undoManager);
        init();
    }

    private void init() {
        this.undoSupport = new UndoableEditSupport();
        this.undoSupport.addUndoableEditListener(undoAdapter);
    }

    public void mementoConfig(UndoableEdit edit) {
        this.undoSupport.postEdit(edit);
    }

}

