package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.cargoeditor.CargoTradeFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Class SettingsPanel - adaptive JPanel that offers data about the selected compartment and specific settings
 * options based on the selected compartment's type;
 */

@Getter
public class SettingsPanel extends JPanel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private BlueprintSelectionModel selectionModel;
    private JTextArea areaData;
    private FuelPanel fuelPanel;
    private CargoPanel cargoPanel;
    private Compartment compartmentArea;
    private static final String listener_Id = EditorCore.generalListenerID;
    @Setter
    private CargoTradeFrame cargoTradeFrame;

    public SettingsPanel(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getBpSelectionModel();
        this.selectionModel.addListener(listener_Id, this);
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        setPreferredSize(new Dimension(500, 130));
        setBorder(BorderFactory.createEtchedBorder());
        createAreaDataComponent();
        createInternalComponents();
    }

    /**
     * Inits, configures and adds the component located in left-upper corner of the frame;
     * The areaData component displays the selected area's data;
     */
    private void createAreaDataComponent(){
        this.areaData = new JTextArea("Selected compartment:\n\nN/A", 7, 20);
        areaData.setBorder(BorderFactory.createEtchedBorder());
        areaData.setWrapStyleWord(true);
        areaData.setLineWrap(true);
        areaData.setEditable(false);
        areaData.setEnabled(false);
        areaData.setDisabledTextColor(Color.white);
        areaData.setFont(Font.getFont(Font.MONOSPACED));
        areaData.setPreferredSize(new Dimension(150,150));
        add(new JScrollPane(areaData));
    }

    /**
     * Initializes and adds both internal setting panels.
     */
    private void createInternalComponents() {
        this.fuelPanel = new FuelPanel(editorCore, this);
        this.cargoPanel = new CargoPanel(editorCore, this);
        add(fuelPanel);
        add(cargoPanel);
        fuelPanel.setVisible(false);
    }

    /**
     *
     * @param area selected compartment area;
     * @param dataTracker class that tracks aircraft data;
     *                    launches area data collection and display
     */

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) {
        this.compartmentArea = area;
        displayPrimaryData(area);
    }
    /**
     *
     * @param area selected compartment - used to supply the data displayed in the areaData
     *             component. Changes are made in real time upon selection real-time;
     */
    @SneakyThrows
    private void displayPrimaryData(Compartment area) {
        this.areaData.setText("Selected compartment:\n");
        for (var field : FileUtils.getAllFields(area.getClass())) {
            field.setAccessible(true);
            this.areaData.append(field.getName().toUpperCase() + ": " + field.get(area).toString() + "\n");
        }
    }
}