package nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.CargoFrame;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * CargoPanel class - minimalistic panel containing the button to launch the cargo trade frame between the user's
 * aircraft and the warehouse;
 */
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
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(800, 120));
        editorCore.getBpSelectionModel().addListener(listenerId, this);
        addCargoLoaderButton();
    }

    /**
     * Initializes the 'load cargo' button, adds and action listener and sets the action to be the launch
     * of the cargo trade JFrame;
     */
    private void addCargoLoaderButton() {
        this.exCargoLoader = new JButton("Load cargo");
        exCargoLoader.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    settingsPanel.setCargoFrame(
                            new CargoFrame(editorCore, (CargoArea) settingsPanel.getCompartmentArea(), CargoPanel.this));
                    exCargoLoader.setEnabled(false);
                    settingsPanel.getController().setCargoMediator(settingsPanel.getCargoFrame().getMediator());
                    settingsPanel.getSelectionModel().setFocusCompartment(settingsPanel.getCompartmentArea());
                });
            }
        });
        add(exCargoLoader);
        setVisible(false);
    }

    /**
     *
     * @param area selected cargo area;
     * @param dataTracker
     *      sets this panel as visible and hides the concurrent fuel panel ;
     */
    @Override
    public void fireBpUpdate(Compartment area, AircraftDataTracker dataTracker) {
        settingsPanel.setActivePanel(this);
    }
}
