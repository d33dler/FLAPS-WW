package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.DepartAction;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;

import javax.swing.*;
import java.awt.*;

/**
 * DepartPanel class - minimalistic panel containing only the depart button;
 */
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

    /**
     * Initialize the Depart button and
     * override the setEnabled method with an additional feature (changing tooltipText);
     */
    private void init() {
        setLayout(new FlowLayout());
        this.departButton = new JButton(new DepartAction(selectionModel, editorCore.getDataTracker())) {

            @Override
            public void setEnabled(boolean b) {
                super.setEnabled(b);
                if (b)
                    setEnabledTip();
                else
                    setDisabledTip();
            }
        };
        departButton.setEnabled(false);
        setDisabledTip();
        add(departButton);
    }

    /**
     * Changes the tool tip text for the button based on its getEnabled state;
     */
    private void setEnabledTip() {
        if (departButton != null)
            this.departButton.setToolTipText(MessagesDb.DEPART_TIP_1);
    }

    private void setDisabledTip() {
        if (departButton != null)
            this.departButton.setToolTipText(MessagesDb.DEPART_TIP_0);
    }

}
