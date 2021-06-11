package nl.rug.oop.flaps.aircraft_editor.view.maineditor;

import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * The main frame in which the editor should be displayed.
 *
 * @author T.O.W.E.R.
 */

@Getter
public class EditorFrame extends EditorWindows implements WindowListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 920;
    public EditorCore editorCore;
    private BlueprintSelectionModel selectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private Aircraft aircraft;
    private BlueprintPanel blueprintPanel;
    private SettingsPanel settingsPanel;
    private LogPanel logPanel;
    private InfoPanel infoPanel;
    private UndoManager undoManager;
    private JButton configInitButton;
    public EditorFrame(Aircraft aircraft, BlueprintSelectionModel selectionModel, AircraftLoadingModel cargoModel) {
        setTitle("Aircraft Editor");
        this.selectionModel = selectionModel;
        this.aircraftLoadingModel = cargoModel;
        this.aircraft = aircraft;
        this.editorCore = new EditorCore(aircraft, selectionModel, this); //TODO code style M before V
        this.configInitButton =  editorCore.getWorld().getFlapsFrame().getAircraftPanel().getOpenConfigurer();
        addWindowListener(this);
        this.undoManager = editorCore.getUndoRedoManager().getUndoManager();
        addLog();
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

    @Override
    public void windowClosed(WindowEvent e) {
        if (settingsPanel.getCargoTradeFrame() != null)
            settingsPanel.getCargoTradeFrame().dispose();
        editorCore.getWorld().getEditorTrack().remove(aircraft);
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

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private void addLog() {
        logPanel = new LogPanel(this);
    }

    private void addMainPanels() {
        blueprintPanel = new BlueprintPanel(editorCore, selectionModel, aircraft);
        settingsPanel = new SettingsPanel(editorCore);
        infoPanel = new InfoPanel(editorCore);
        editorCore.getDataTracker().setDisplay(blueprintPanel.getBlueprintDisplay());
        editorCore.getDataTracker().setDepartPanel(logPanel.getDepartPanel());
        add(this.blueprintPanel, BorderLayout.WEST);
        add(this.settingsPanel, BorderLayout.NORTH);
        add(this.infoPanel, BorderLayout.CENTER);
        add(this.logPanel, BorderLayout.SOUTH);
        validate();
    }


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
}
