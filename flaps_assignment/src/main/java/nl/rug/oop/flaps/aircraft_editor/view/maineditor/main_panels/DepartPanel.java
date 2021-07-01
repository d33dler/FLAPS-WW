package nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view.FlightSimFrame;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

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

        setDisabledTip();
        this.departButton = new JButton("F.L.A.P.S.") {
            @Override
            public void setEnabled(boolean b) {
                super.setEnabled(b);
                if (b)
                    setEnabledTip();
                else
                    setDisabledTip();
            }
        };
        departButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() ->
                        editorCore.setFlightSimFrame(new FlightSimFrame(editorCore)));

            }
        });
        editorCore.getDataTracker().setDepartPanel(this);
        editorCore.getDataTracker().performDepartureValidationCheck();
        departButton.setFocusPainted(false);
        departButton.setBackground(new Color(46, 57, 213));
        departButton.setFont(new Font("Tahoma", Font.BOLD, 13));
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
