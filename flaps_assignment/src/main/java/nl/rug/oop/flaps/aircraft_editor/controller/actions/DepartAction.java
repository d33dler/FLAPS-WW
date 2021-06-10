package nl.rug.oop.flaps.aircraft_editor.controller.actions;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
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
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        var sm = this.selectionModel;
        if (sm.getSelectedAirport() != null && sm.getSelectedAircraft() != null && sm.getSelectedDestinationAirport() != null) {
            var aircraft = sm.getSelectedAircraft();
            var start = sm.getSelectedAirport();
            var end = sm.getSelectedDestinationAirport();
            notifyDepart(start);
            if (aircraft.getType().getTakeoffClipPath() != null) {
                this.playTakeoffClip(aircraft.getType());
            }
            start.removeAircraft(aircraft);
            end.addAircraft(aircraft);
            sm.setSelectedAirport(end);
            sm.setSelectedDestinationAirport(null);
            sm.setSelectedAircraft(aircraft);
            aircraft.removeFuel(aircraft.getFuelConsumption(dataTracker.getTravelDistance()));
            aircraft.removeAllCargo();
            notificationPopUp();
            dataTracker.getEditorCore().getAircraftLoadingModel().fireFuelUpdate();
            dataTracker.getEditorCore().getAircraftLoadingModel().fireCargoUpdate();
        }
    }


    private void notifyDepart(Airport airport) {
        JOptionPane.showMessageDialog(editorFrame, "Your plane is preparing for departure from  " +
               airport.getName());
    }

    @SneakyThrows
    private void notificationPopUp() {
        Thread.sleep(4000);
        JOptionPane.showMessageDialog(editorFrame, "Your plane has arrived at " +
                this.selectionModel.getSelectedAirport().getName() );
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
