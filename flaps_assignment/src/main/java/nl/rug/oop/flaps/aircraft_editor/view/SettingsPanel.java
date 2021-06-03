package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.*;
import java.awt.*;

@Getter
public class SettingsPanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private BlueprintSelectionModel selectionModel;
    private JTextArea areaData;
    private FuelPanel fuelPanel;
    private CargoPanel cargoPanel;
    private Compartment compartmentArea;
    private JButton exCargoLoader;

    @Setter
    private CargoSettings cargoSettings;
    
    public SettingsPanel(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getSelectionModel();
        this.selectionModel.addListener(this);
        init();
    }

    private void init() {
        setLayout(new FlowLayout());
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setPreferredSize(new Dimension(500, 150));
        setBorder(BorderFactory.createEtchedBorder());
        this.areaData = new JTextArea("Selected compartment:\n\nN/A",7,17);
        areaData.setBorder(BorderFactory.createEtchedBorder());
        areaData.setWrapStyleWord(true);
        areaData.setLineWrap(true);
        areaData.setEditable(false);
        areaData.setEnabled(false);
        areaData.setDisabledTextColor(Color.white);
        areaData.setFont(Font.getFont(Font.MONOSPACED));
        this.fuelPanel = new FuelPanel(editorCore, this );
        this.cargoPanel = new CargoPanel(editorCore,this);
        add(new JScrollPane(areaData));
        add(fuelPanel);
        add(cargoPanel);
        fuelPanel.setVisible(false);
    }

    @Override
    public void compartmentSelected(Compartment area) {
        BlueprintSelectionListener.super.compartmentSelected(area);
        this.compartmentArea = area;
        displayPrimaryData(area);
    }

    @SneakyThrows
    private void displayPrimaryData(Compartment area) {
        this.areaData.setText("Selected compartment:\n");
        for (var field : FileUtils.getAllFields(area.getClass())) {
            field.setAccessible(true);
            this.areaData.append("\n" + field.getName().toUpperCase() + ": " + field.get(area).toString());
        }
    }
}