package nl.rug.oop.flaps.flightsim.sim_view;

import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.airport.Airport;

import javax.swing.*;
import java.awt.*;

@Getter
public class FlightSimFrame extends JFrame {

    private final FlightSimApplication flightSimApp;
    private final EditorCore editorCore;
    public Airport origin;
    public Airport destination;
    private MapViewControls mapViewControls;
    private QuickAccessPanel accessPanel;
    private SimulatorWindow simulatorWindow;
    @Setter
    private Globe earth;
    @Setter
    private ObjRenderable aircraftObj_3d;
    private FlightSimCore core;

    public FlightSimFrame(EditorCore editorCore) {
        setTitle("F.L.A.P.S Flight Simulator");
        this.editorCore = editorCore;
        this.flightSimApp = new FlightSimApplication();
        this.core = new FlightSimCore(editorCore, flightSimApp,this);
        this.simulatorWindow = core.getSimulatorWindow();
        flightSimApp.setCore(core);
        init();
    }

    @SneakyThrows
    private void init() {
        setCoords();
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1920, 1080));
        initWorld();
        addViewControls();
        addQuickAccessPanel();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void setCoords() {
        this.origin = core.origin;
        this.destination = core.destination;
    }

    private void initWorld() {
        getContentPane().add(simulatorWindow, BorderLayout.CENTER);
        setIntroAnimation();
    }

    private void addQuickAccessPanel() {
        this.accessPanel = new QuickAccessPanel(this, simulatorWindow.getOrbitView(), editorCore);
        add(accessPanel, BorderLayout.WEST);
    }

    private void addViewControls() {
        this.mapViewControls = new MapViewControls(flightSimApp);
    }

    private void setIntroAnimation() {
        double lat = core.og.getLatitude();
        double longitude = core.og.getLongitude();
        simulatorWindow.getOrbitView().
                addEyePositionAnimator(4000, Position.fromDegrees(0, 30, 1e8),
                        Position.fromDegrees(lat, longitude, 4e3));
    }
}
