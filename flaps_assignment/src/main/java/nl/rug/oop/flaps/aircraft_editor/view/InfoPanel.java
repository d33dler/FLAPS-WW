package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.DataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import javax.swing.*;

@Getter
@Setter
public class InfoPanel extends JPanel {
    EditorCore model;
    Aircraft aircraft;
    DataTracker dataTracker;

    public InfoPanel(EditorCore editorCore) {
        this.model = editorCore;
        this.aircraft = editorCore.getAircraft();
        this.dataTracker = editorCore.getDataTracker();
    }

    private void init() {

    }
}
