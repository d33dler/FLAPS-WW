package nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

import javax.swing.*;
import java.awt.*;

public class EnginePanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private SettingsPanel settingsPanel;
    @Getter
    @Setter
    private JButton engineWs;
    private final static String listenerId = EditorCore.engineListenerID;

    public EnginePanel(EditorCore editorCore, SettingsPanel settingsPanel) {
        this.editorCore = editorCore;
        this.settingsPanel = settingsPanel;
        init();
    }

    private void init() {
        editorCore.getBpSelectionModel().addListener(listenerId, this);
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setPreferredSize(new Dimension(750, 120));
        setVisible(false);
    }

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
       settingsPanel.setActivePanel(this);
    }
}
