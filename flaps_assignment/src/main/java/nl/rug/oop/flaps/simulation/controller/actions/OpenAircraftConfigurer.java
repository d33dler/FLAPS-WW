package nl.rug.oop.flaps.simulation.controller.actions;

import nl.rug.oop.flaps.aircraft_editor.model.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.model.AircraftLoadingModel;
import nl.rug.oop.flaps.aircraft_editor.view.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author T.O.W.E.R.
 */
public class OpenAircraftConfigurer extends AbstractAction implements PropertyChangeListener {

    private final Aircraft aircraft;
    private final BlueprintSelectionModel selectionModel;
    private final AircraftLoadingModel aircraftLoadingModel;

    public OpenAircraftConfigurer(Aircraft aircraft) {
        super("Configure");
        this.aircraft = aircraft;
        this.selectionModel = new BlueprintSelectionModel();
        this.aircraftLoadingModel = new AircraftLoadingModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> new EditorFrame(aircraft, selectionModel, aircraftLoadingModel));
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }
}
