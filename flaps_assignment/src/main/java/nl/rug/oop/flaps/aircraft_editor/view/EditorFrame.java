package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.CargoManipulationModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import javax.swing.*;
import java.awt.*;

/**
 * The main frame in which the editor should be displayed.
 *
 * @author T.O.W.E.R.
 */

@Getter
public class EditorFrame extends JFrame {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 900;
    public EditorCore model;
    private BlueprintSelectionModel selectionModel;
    private CargoManipulationModel cargoManipulationModel;
    private Aircraft aircraft;
    private BlueprintPanel blueprintPanel;
    private SettingsPanel settingsPanel;
    private LogPanel logPanel;
    private InfoPanel infoPanel;


    public EditorFrame(Aircraft aircraft, BlueprintSelectionModel selectionModel, CargoManipulationModel cargoModel) {
        super("Aircraft Editor");
        this.selectionModel = selectionModel;
        this.cargoManipulationModel = cargoModel;
        this.aircraft = aircraft;
        addLog();
        this.model = new EditorCore(aircraft, selectionModel, this);
        editorInit();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void editorInit() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMainPanels();
        addMenuBar();
    }
    private void addLog() {
        logPanel = new LogPanel(this);
    }

    private void addMainPanels() {
        blueprintPanel = new BlueprintPanel(model, selectionModel, this.aircraft);
        settingsPanel = new SettingsPanel(model);
        infoPanel = new InfoPanel(model);
        add(this.blueprintPanel, BorderLayout.WEST);
        add(this.settingsPanel, BorderLayout.NORTH);
        add(this.infoPanel, BorderLayout.EAST);
        add(this.logPanel, BorderLayout.SOUTH);
        validate();
    }

    private void addMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");
        //  JMenu fileFormat = new JMenu("Export to...");
        // JMenuItem json = new JMenuItem(new MenuAction("JSON", this));
        //  fileFormat.add(json);
        //  menu.add(fileFormat);
        bar.add(menu);
        setJMenuBar(bar);
    }
}
