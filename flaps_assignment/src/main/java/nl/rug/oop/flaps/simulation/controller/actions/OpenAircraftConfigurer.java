package nl.rug.oop.flaps.simulation.controller.actions;

import nl.rug.oop.flaps.aircraft_editor.model.listener_models.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.World;
import nl.rug.oop.flaps.simulation.view.panels.aircraft.AircraftPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;

/**
 * @author T.O.W.E.R.
 */
public class OpenAircraftConfigurer extends AbstractAction implements PropertyChangeListener {

    private final Aircraft aircraft;
    private final BlueprintSelectionModel selectionModel;
    private final AircraftLoadingModel aircraftLoadingModel;
    private final Map<Aircraft,Integer > editorTrack;
    private final AircraftPanel aircraftPanel;
    private final World world;

    public OpenAircraftConfigurer(AircraftPanel aircraftPanel, Aircraft aircraft, World world) {
        super("Configure");
        this.aircraft = aircraft;
        this.selectionModel = new BlueprintSelectionModel();
        this.aircraftLoadingModel = new AircraftLoadingModel();
        this.world = world;
        this.aircraftPanel = aircraftPanel;
        this.editorTrack = world.getEditorTrack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int hash = aircraftPanel.getOpenConfigurer().hashCode();
        if (world.getSelectionModel().getSelectedDestinationAirport() != null) {
            if (editorTrack.get(aircraft) == null
                    && world.getSelectionModel().
                    getSelectedDestinationAirport().canAcceptIncomingAircraft()) {
                SwingUtilities.invokeLater(
                        () -> new EditorFrame(aircraft, selectionModel, aircraftLoadingModel));
                editorTrack.put(aircraft,hash);
                world.getSelectionModel().getSelectedAirport().setHasEditor(true);
                world.getFlapsFrame().getWorldPanel().repaint();
                super.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(world.getFlapsFrame(),
                        MessagesDb.AIRPORT_CLOSED);
            }
        } else {
            JOptionPane.showMessageDialog(world.getFlapsFrame(),
                    MessagesDb.AIRPORT_NIL);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
