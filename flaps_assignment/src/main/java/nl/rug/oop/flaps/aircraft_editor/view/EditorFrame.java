package nl.rug.oop.flaps.aircraft_editor.view;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;

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
    private static final int HEIGHT = 700;
    private EditorCore model;
    private WorldSelectionModel selectionModel;
    private Aircraft aircraft;
    private BlueprintPanel blueprintPanel;
    private SettingsPanel settingsPanel;
    private InfoPanel infoPanel;

    public EditorFrame(Aircraft aircraft, WorldSelectionModel selectionModel) {
        super("Aircraft Editor");
        this.selectionModel = selectionModel;
        this.aircraft = aircraft;
        //setFocusTraversalPolicy(null);
        editorInit();
        pack();
        setLocationRelativeTo(null);
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

    private void addMainPanels() {
        blueprintPanel = new BlueprintPanel(model,selectionModel, this.aircraft);
        settingsPanel = new SettingsPanel(model);
        infoPanel = new InfoPanel(model);
        add(this.blueprintPanel, BorderLayout.WEST);
        add(this.settingsPanel, BorderLayout.EAST);
        add(this.infoPanel, BorderLayout.SOUTH);
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





/*
 * To get the destination airport:
 * selectionModel.getSelectedDestinationAirport()
 * To get the origin airport:
 * selectionModel.getSelectedAirport()
 * Other than this, the only place where you you need to use the selectionModel is passing it to the DepartAction
 */