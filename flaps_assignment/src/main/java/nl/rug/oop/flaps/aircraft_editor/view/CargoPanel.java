package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CargoPanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private SettingsPanel settingsPanel;
    @Getter
    @Setter
    private JButton exCargoLoader;
    private final static String listenerId = EditorCore.cargoListenerID;

    public CargoPanel(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.settingsPanel = settingsPanel;
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(1000, 120));
        editorCore.getSelectionModel().addListener(listenerId, this);
        addCargoLoaderButton();
    }

    private void addCargoLoaderButton() {
        this.exCargoLoader = new JButton("Load cargo");
        exCargoLoader.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() ->
                        settingsPanel.setCargoSettings(
                                new CargoSettings(editorCore, (CargoArea) settingsPanel.getCompartmentArea(),CargoPanel.this)));
            }
        });
        add(exCargoLoader);
        setVisible(false);
    }

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        editorCore.getConfigurator().updateDatabaseTables(settingsPanel);
        settingsPanel.getFuelPanel().setVisible(false);
        setVisible(true);
    }
}
