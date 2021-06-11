package nl.rug.oop.flaps.simulation.controller.actions;

import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.world.World;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

/**
 * @author T.O.W.E.R.
 */
public class OpenAircraftConfigurer extends AbstractAction implements PropertyChangeListener {

    private final Aircraft aircraft;
    private final BlueprintSelectionModel selectionModel;
    private final AircraftLoadingModel aircraftLoadingModel;
    private final HashSet<Aircraft> editorTrack;
    private World world;

    public OpenAircraftConfigurer(Aircraft aircraft, World world) {
        super("Configure");
        this.aircraft = aircraft;
        this.selectionModel = new BlueprintSelectionModel();
        this.aircraftLoadingModel = new AircraftLoadingModel();
        this.world = world;
        this.editorTrack = world.getEditorTrack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (world.getSelectionModel().getSelectedDestinationAirport() != null) {
            if (!editorTrack.contains(aircraft)
                    && world.getSelectionModel().
                    getSelectedDestinationAirport().canAcceptIncomingAircraft()) {
                SwingUtilities.invokeLater(
                        () -> new EditorFrame(aircraft, selectionModel, aircraftLoadingModel));
                editorTrack.add(aircraft);
                super.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(world.getFlapsFrame(),
                        "The selected airport does not accept incoming aircraft!");
            }
        } else {
            JOptionPane.showMessageDialog(world.getFlapsFrame(),
                    "Please select a destination airport before initializing the Aircraft Editor© !");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
