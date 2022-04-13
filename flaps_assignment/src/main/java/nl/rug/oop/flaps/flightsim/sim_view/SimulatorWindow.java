package nl.rug.oop.flaps.flightsim.sim_view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.view.firstperson.BasicFlyView;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.swing.*;
import java.awt.*;

@Getter
public class SimulatorWindow extends JPanel implements SimWindowCallbackListener {
    private FlightSimCore core;
    private FlightSimFrame flightSimFrame;
    private FlightSimApplication flightSimApp;
    private EditorCore editorCore;
    private WorldWindow worldWindow;
    private BasicOrbitView orbitView;
    private BasicFlyView basicFlyView;
    private ApplicationTemplate.AppPanel worldPanel;
    private Globe earth;
    public GeographicCoordinates og, dest;
    private SimLayersLoader layersLoader;

    public SimulatorWindow(FlightSimCore core, FlightSimFrame flightSimFrame, EditorCore editorCore) {
        this.core = core;
        this.flightSimFrame = flightSimFrame;
        this.flightSimApp = flightSimFrame.getFlightSimApp();
        this.editorCore = editorCore;
        init();
    }

    private void init() {
        this.og = core.getOg();
        this.dest = core.getDest();
        initPhase2();
    }

    private void initPhase2() {
        initSimPanel();
    }


    @SneakyThrows
    private void initSimPanel() {
        this.worldPanel = flightSimApp.init(this);
        this.worldWindow = worldPanel.getWwd();
        this.orbitView = (BasicOrbitView) worldWindow.getView();
        orbitView.simWindowChangeModel.addListener(this);
        this.basicFlyView = flightSimApp.getBasicFlyView();
        worldPanel.setSize(new Dimension(1920, 1080));
        this.earth = worldWindow.getModel().getGlobe();
        this.layersLoader = new SimLayersLoader(core,this);
        add(worldPanel);
    }

    @Override
    public void windowStateChanged(Position currentEyePos) {
    }
}
