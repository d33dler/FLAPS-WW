package nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SettingsPanel;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.PassengersFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Cabin;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PassengerPanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private SettingsPanel settingsPanel;
    private final static String listenerId = EditorCore.passengerListenerID;
    @Getter
    @Setter
    private JButton embarque;
    private PassengersFrame boardFrame;
    private Cabin cabin;
    public PassengerPanel(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.settingsPanel = settingsPanel;
        init();
    }

    private void init() {
        editorCore.getBpSelectionModel().addListener(listenerId, this);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(750, 120));
        setEmbarqueButton();
        setVisible(false);
    }
    private void setEmbarqueButton() {
        this.embarque = new JButton("Boarding");
        embarque.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    embarque.setEnabled(false);
                    boardFrame = new PassengersFrame(editorCore, cabin, PassengerPanel.this);
                    settingsPanel.setPassengersFrame(boardFrame);
                    settingsPanel.getController().setPassengerMediator(boardFrame.getMediator());
                    settingsPanel.getSelectionModel().setFocusCompartment(settingsPanel.getCompartmentArea());
                });
            }
        });
        add(embarque);
    }

    @Override
    public void fireBpUpdate(Compartment area, AircraftDataTracker dataTracker) {
        this.cabin = (Cabin) area;
        settingsPanel.setActivePanel(this);
    }
}
