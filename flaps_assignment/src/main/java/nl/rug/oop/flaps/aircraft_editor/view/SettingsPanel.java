package nl.rug.oop.flaps.aircraft_editor.view;

import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel implements BlueprintSelectionListener {
    EditorCore model;
    BlueprintSelectionModel selectionModel;
    JSlider fuelSlider;
    JTextArea areaData;
    Compartment compartmentArea;

    public SettingsPanel(EditorCore model) {
        this.model = model;
        this.selectionModel = model.getSelectionModel();
        this.selectionModel.addListener(this);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        areaData = new JTextArea();
        areaData.setWrapStyleWord(true);
        areaData.setLineWrap(true);
        areaData.setEditable(false);
        areaData.setEnabled(false);
        areaData.setDisabledTextColor(Color.white);
        areaData.setFont(Font.getFont(Font.MONOSPACED));
        add(areaData, BorderLayout.WEST);
    }


}
