package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.AircraftLoader;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.swing.*;
import java.awt.*;

@Getter
public class SimulatorWindow extends JPanel implements SimWindowCallbackListener {
    private FlightSimFrame flightSimFrame;
    private FlightSimApplication flightSimApp;
    private EditorCore editorCore;
    private WorldWindow worldWindow;
    private BasicOrbitView orbitView;
    private ApplicationTemplate.AppPanel mapPanel;
    private Globe earth;
    private ObjRenderable aircraftObj_3d;
    private GeographicCoordinates og, dest;

    public SimulatorWindow(FlightSimFrame flightSimFrame, EditorCore editorCore) {
        this.flightSimFrame = flightSimFrame;
        this.flightSimApp = flightSimFrame.getFlightSimApp();
        this.editorCore = editorCore;
        init();
    }

    private void init() {
        this.og = flightSimFrame.getOg();
        this.dest = flightSimFrame.getDest();
        initPhase2();
    }

    private void initPhase2() {
        initSimPanel();
        initAircraft();
    }

    private void initAircraft() {
        this.aircraftObj_3d = new AircraftLoader(worldWindow).loadAircraft(worldWindow, earth, og);
    }

    @SneakyThrows
    private void initSimPanel() {
        this.mapPanel = flightSimApp.init();
        this.worldWindow = mapPanel.getWwd();
        this.orbitView = (BasicOrbitView) worldWindow.getView();
        orbitView.simWindowChangeModel.addListener(this);
        mapPanel.setPreferredSize(new Dimension(1024, 768));
        this.earth = worldWindow.getModel().getGlobe();
        add(mapPanel);
    }

    @Override
    public void windowStateChanged(Position currentEyePos) {
        //TODO
    }
}
