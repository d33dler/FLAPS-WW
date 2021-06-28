package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Controller;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print.BlueprintPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.InfoPanel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.Logger;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.SettingsPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * EditorFrame class - the main frame of the aircraft editor.
 */

@Getter
public class EditorFrame extends EditorWindows implements WindowListener {
    public EditorCore editorCore;
    private BlueprintSelectionModel blueprintSelectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private Aircraft aircraft;
    private BlueprintPanel blueprintPanel;
    private SettingsPanel settingsPanel;
    private Logger logger;
    private InfoPanel infoPanel;
    private UndoManager undoManager;
    private JButton configInitButton;
    private AircraftDataTracker dataTracker;
    private Controller controller;
    private static final int WIDTH = 1200, HEIGHT = 920;

    public EditorFrame(Aircraft aircraft, BlueprintSelectionModel blueprintSelectionModel, AircraftLoadingModel cargoModel) {
        setTitle("Aircraft Editor");
        addWindowListener(this);
        this.blueprintSelectionModel = blueprintSelectionModel;
        this.aircraftLoadingModel = cargoModel;
        this.aircraft = aircraft;
        this.editorCore = new EditorCore(aircraft, blueprintSelectionModel, this); //TODO code style M before V
        this.controller = editorCore.getController();
        this.configInitButton = editorCore.getWorld().getFlapsFrame().getAircraftPanel().getOpenConfigurer();
        this.undoManager = editorCore.getUndoRedoManager().getUndoManager();
        editorInit();
    }

    private void editorInit() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addLog();
        addMainPanels();
        addMenuBar();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    /**
     * Initialize the logPanel and set the field value for the blueprintSelectionModel
     */
    private void addLog() {
        logger = new Logger(this);
        blueprintSelectionModel.setLogger(logger);
    }

    /**
     * Initializes and adds all main panels to the frame
     */
    private void addMainPanels() {
        blueprintPanel = new BlueprintPanel(editorCore, blueprintSelectionModel, aircraft);
        settingsPanel = new SettingsPanel(editorCore);
        infoPanel = new InfoPanel(editorCore);
        setTrackerPanels();
        add(this.blueprintPanel, BorderLayout.WEST);
        add(this.settingsPanel, BorderLayout.NORTH);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.logger, BorderLayout.SOUTH);
        validate();
    }

    private void setTrackerPanels() {
        this.dataTracker = editorCore.getDataTracker();
        dataTracker.setDisplay(blueprintPanel.getBlueprintDisplay());
        dataTracker.setDepartPanel(logger.getDepartPanel());
        dataTracker.setInfoPanel(infoPanel);
    }

    /**
     * Initializes and adds the menu bar
     */
    private void addMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        menu.add(new JMenuItem(new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EditorFrame.this.undoManager.canUndo()) {
                    EditorFrame.this.undoManager.undo();
                }
            }
        }));
        menu.add(new JMenuItem(new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EditorFrame.this.undoManager.canRedo()) {
                    EditorFrame.this.undoManager.redo();
                }
            }
        }));
        bar.add(menu);
        setJMenuBar(bar);
    }

    /**
     * All the methods below are overridden in order to limit the user ability to launch
     * only only a single editor per aircraft, but preserve the ability to open multiple editors
     * at once for different aircraft;
     */
    @Override
    public void windowClosed(WindowEvent e) {
        if (settingsPanel.getCargoFrame() != null) {
            settingsPanel.getCargoFrame().dispose();
        }
        editorCore.getWorld().getEditorTrack().remove(aircraft);
        this.windowClosing(e);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        boolean check = editorCore.getWorld().getSelectionModel().getSelectedAircraft().
                getIdentifier().equals(aircraft.getIdentifier());
        if (check) {
            configInitButton.setEnabled(true);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        configInitButton.setEnabled(false);
    }
}
