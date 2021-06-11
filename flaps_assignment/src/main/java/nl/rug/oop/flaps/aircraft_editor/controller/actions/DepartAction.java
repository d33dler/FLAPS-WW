package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.view.cargoeditor.CargoTradeFrame;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.LogPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.AircraftType;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.world.WorldSelectionModel;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * This action is invoked to perform the actual departure of an aircraft from an
 * airport.
 * <p>
 * Use the setEnabled() method to enable/disable the corresponding button
 *
 * @author T.O.W.E.R.
 */
@Log
public class DepartAction extends AbstractAction {
    private final WorldSelectionModel selectionModel;
    private AircraftDataTracker dataTracker;
    private EditorFrame editorFrame;

    public DepartAction(WorldSelectionModel selectionModel, AircraftDataTracker dataTracker) {
        super("Depart");
        this.selectionModel = selectionModel;
        this.dataTracker = dataTracker;
        this.editorFrame = dataTracker.getEditorCore().getEditorFrame();
    }

    /**
     * Performs a series of methods described below;
     * We removed the conditional constraints since these constraints were set at the initialization of the
     * aircraft editor.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        var sm = this.selectionModel;
        var aircraft = sm.getSelectedAircraft();
        LogPanel logPanel = editorFrame.getLogPanel();
        super.setEnabled(false);
        hideFrames();
        logPanel.notifyDepart(Airport.changeLocation(sm, aircraft));
        if (aircraft.getType().getTakeoffClipPath() != null) {
            this.playTakeoffClip(aircraft.getType());
        }
        logPanel.notifyArrive(sm.getSelectedAirport());
        showEditor();
        initAircraftUpdate(aircraft, logPanel);
        closeOperations(logPanel);

    }

    /**
     * Hides the main editor frame and disposes of the cargo trade frame if it is open;
     */
    private void hideFrames() {
        editorFrame.setVisible(false);
        CargoTradeFrame tradeFrame = editorFrame.getSettingsPanel().getCargoTradeFrame();
        if (tradeFrame != null) {
            tradeFrame.dispose();
        }
    }

    /**
     * Sets the editor as visible.
     */
    private void showEditor() {
        editorFrame.setVisible(true);
        editorFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     *
     * @param aircraft aircraft the cargo of which must be unloaded;
     * @param logPanel used to relay information;
     */
    private void initAircraftUpdate(Aircraft aircraft, LogPanel logPanel) {
        aircraft.unLoadAircraft(aircraft, dataTracker, logPanel);
        dataTracker.getEditorCore().getAircraftLoadingModel().fireAllUpdates();
    }

    /**
     *
     * @param logPanel - contains the methods used to inform the user about the list of unloaded cargo
     *                 etc;
     *                 Disposes of the editor frame upon conclusion;
     */
    @SneakyThrows
    private void closeOperations(LogPanel logPanel) {
        logPanel.notifyUnloadList();
        logPanel.notifyArrivalUpdates(selectionModel.getSelectedAirport());
        editorFrame.dispose();
    }

    private void playTakeoffClip(AircraftType type) {
        new Thread(() -> {
            try (Clip clip = AudioSystem.getClip()) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(type.getTakeoffClipPath().toFile());
                clip.open(ais);
                clip.start();
                Thread.sleep((long) (ais.getFrameLength() / ais.getFormat().getFrameRate()) * 1000);
            } catch (Exception e) {
                log.warning("Could not play takeoff clip: " + e.getMessage());
            }
        }).start();
    }
}
