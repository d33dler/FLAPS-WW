package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.DepartAction;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModelListener;

import javax.swing.*;
import java.awt.*;

public class DepartPanel extends JPanel {
    private EditorCore editorCore;
    private WorldSelectionModel selectionModel;

    @Getter
    @Setter
    private JButton departButton;

    public DepartPanel(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getWorld().getSelectionModel();
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        this.departButton = new JButton(new DepartAction(selectionModel));
        add(departButton);
    }
}
