package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.swing.*;
import java.awt.*;

@Getter
public class FlightSimFrame extends JFrame {

    private final FlightSimApplication flightSimApp;
    private final EditorCore editorCore;
    private final Airport origin;
    private final Airport destination;
    private GeographicCoordinates og, dest;
    private MapViewControls mapViewControls;
    private QuickAccessPanel accessPanel;
    private SimulatorWindow simulatorWindow;
    @Setter
    private Globe earth;
    @Setter
    private ObjRenderable aircraftObj_3d;

    public FlightSimFrame(EditorCore editorCore) throws HeadlessException {
        setTitle("F.L.A.P.S Flight Simulator");
        this.editorCore = editorCore;
        this.flightSimApp = new FlightSimApplication();
        this.origin = editorCore.getSource();
        this.destination = editorCore.getDestination();
        init();
    }

    @SneakyThrows
    private void init() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 1200));
        initWorld();
        addViewControls();
        addQuickAccessPanel();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void initWorld() {
        initAirports();
        initSimWindow();
        getContentPane().add(simulatorWindow, BorderLayout.CENTER);
        setPathStart();
    }

    private void initSimWindow() {
        this.simulatorWindow = new SimulatorWindow(this, editorCore);
        this.earth = simulatorWindow.getEarth();
        this.aircraftObj_3d = simulatorWindow.getAircraftObj_3d();
    }

    private void addQuickAccessPanel() {
        this.accessPanel = new QuickAccessPanel(this, simulatorWindow.getOrbitView() , editorCore);
        add(accessPanel, BorderLayout.WEST);
    }

    private void initAirports() {
        this.og = origin.getGeographicCoordinates();
        this.dest = destination.getGeographicCoordinates();
    }

    private void addViewControls() {
        this.mapViewControls = new MapViewControls(flightSimApp);
    }

    private void setPathStart() {
        double lat = og.getLatitude();
        double longitude = og.getLongitude();
        simulatorWindow.getOrbitView().
                addEyePositionAnimator(4000, Position.fromDegrees(0, 30, 1e8),
                Position.fromDegrees(lat, longitude, 4e4));
    }
}
