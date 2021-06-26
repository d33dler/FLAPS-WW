package nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.cargo_editor.CargoTradeFrame;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.CargoPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.EnginePanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.FuelPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.area_panels.PassengerPanel;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.PassengerBoardFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;
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
    private Compartment compartmentArea;
    private static final String listener_Id = EditorCore.generalListenerID;
    @Setter
    private CargoTradeFrame cargoTradeFrame;
    @Setter
    private PassengerBoardFrame passengerBoardFrame;
    private JPanel activePanel;

    public SettingsPanel(EditorCore editorCore) {
        this.editorCore = editorCore;
        this.selectionModel = editorCore.getBpSelectionModel();
        this.selectionModel.addListener(listener_Id, this);
        init();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
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
    private void createAreaDataComponent() {
        this.areaData = new JTextArea("Selected compartment:\n\nN/A");
        areaData.setBorder(BorderFactory.createEtchedBorder());
        areaData.setLineWrap(true);
        areaData.setEditable(false);
        areaData.setEnabled(false);
        areaData.setDisabledTextColor(Color.white);
        areaData.setFont(Font.getFont(Font.MONOSPACED));
        add(new JScrollPane(areaData));
    }

    /**
     * Initializes and adds both internal setting panels.
     */
    private void createInternalComponents() {
        add(new FuelPanel(editorCore, this));
        add(new CargoPanel(editorCore, this));
        add(new EnginePanel(editorCore, this));
        add(new PassengerPanel(editorCore,this));
    }

    /**
     * @param area        selected compartment area;
     * @param dataTracker class that tracks aircraft data;
     *                    launches area data collection and display
     */

    @Override
    public void fireBpUpdate(Compartment area, AircraftDataTracker dataTracker) {
        this.compartmentArea = area;
        displayPrimaryData(area);
    }

    /**
     * @param area selected compartment - used to supply the data displayed in the areaData
     *             component. Changes are made in real time upon selection real-time;
     */
    @SneakyThrows
    private void displayPrimaryData(Compartment area) {
        this.areaData.setText("Selected compartment:\n");
        FileUtils.getAllFields(area.getClass()).forEach(field -> System.out.println(field.getName()));
        for (var field : FileUtils.getAllFields(area.getClass())) {
            field.setAccessible(true);
            this.areaData.append(FileUtils.toNiceCase(field.getName()) + ": " + field.get(area).toString() + "\n");
        }
    }

    public void setActivePanel(JPanel activePanel) {
        if (this.activePanel != null)
            this.activePanel.setVisible(false);
        this.activePanel = activePanel;
        activePanel.setVisible(true);
    }
}