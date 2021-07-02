package nl.rug.oop.flaps.flightsim.sim_view;

import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.DepartAction;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.view.generic_panels.GenericButtonPanel;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.flightsim.sim_model.POVChangeModel;
import nl.rug.oop.flaps.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QuickAccessPanel extends GenericButtonPanel implements SimWindowCallbackListener {
    private POVChangeModel povChangeModel;
    private FlightSimCore core;
    private FlightSimFrame flightSimFrame;
    private FlightSimApplication flightSimApp;
    private BasicOrbitView orbitView;
    private EditorCore editorCore;
    private JTextField rollField, azimuthField, elevateField;
    private ObjRenderable aircraft3D, hangar3d;
    private JPanel controlPanel;
    private Globe planet;
    private static final double HEIGHT_LIM = 8e4, ZOOMOUT_LIM = 90;
    private JButton loadPlane;
    private GeographicCoordinates og, dest;
    private SimulatorWindow simulatorWindow;
    public final static long TRAVEL_TIME = 40000;

    public QuickAccessPanel(FlightSimFrame flightSimFrame, BasicOrbitView orbitView, EditorCore editorCore) {
        this.flightSimFrame = flightSimFrame;
        this.core = flightSimFrame.getCore();
        this.orbitView = orbitView;
        this.simulatorWindow = core.getSimulatorWindow();
        this.editorCore = editorCore;
        this.planet = core.getEarth();
        this.flightSimApp = core.getSimApp();
        orbitView.simWindowChangeModel.addListener(this);
        init();
    }

    private void init() {
        addButtons();
    }

    private void addButtons() {
        this.og =
                flightSimFrame.getOrigin().getGeographicCoordinates();
        this.dest =
                flightSimFrame.getDestination().getGeographicCoordinates();
        this.loadPlane = new JButton("Spawn aircraft");
        loadPlane.setToolTipText("In order to load the plane you must be located near the origin airport");
        loadPlane.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPlane.setEnabled(false);
                loadAircraft(og.getLatitude(), og.getLongitude());
                buttonList.get("Depart").setEnabled(true);
            }
        });
        loadPlane.setEnabled(false);

        JButton zoomIn = new JButton("Zoom IN");
        zoomIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationUtils.zoomIn(orbitView, flightSimApp, -15);
            }
        });
        JButton zoomOut = new JButton("Zoom OUT");
        zoomOut.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimulationUtils.zoomOut(orbitView, flightSimApp, 15);
            }
        });

        this.controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setPreferredSize(new Dimension(200, 500));
        controlPanel.add(loadPlane);
        controlPanel.add(zoomIn);
        controlPanel.add(zoomOut);
        controlPanel.add((newButton("Depart", () ->
                        initDeparture(dest.latitude, dest.longitude),
                false, GenericButtonPanel.SET_OFF)));
        add(controlPanel, BorderLayout.WEST);
    }


    private void loadAircraft(double lat, double lon) {
        aircraft3D = core.getModels3DLoader().getAircraft3d_obj();
        double elevation = SimulationUtils.getElevation(planet, lat, lon);
        aircraft3D.setPosition(Position.fromDegrees(lat, lon, elevation));
        SimulationUtils.animatedPanBack(orbitView, flightSimApp, 0.1);
        SimulationUtils.animateAircraftFocus(orbitView, aircraft3D);
        aircraft3D.setVisible(true);
        orbitView.simWindowChangeModel.callbackListeners.remove(this);
    }

    private void loadHangar(boolean b) {
        if (b) {
            double lat = og.latitude;
            double lon = og.longitude;
            hangar3d = core.getModels3DLoader().getHangar3d_obj();
            double elevation = SimulationUtils.getElevation(planet, lat, lon);
            hangar3d.setPosition(Position.fromDegrees(lat, lon, elevation));
            hangar3d.setVisible(true);
        }
    }

    @SneakyThrows
    private void initDeparture(double lat, double longitude) {
        Position destPos = Position.fromDegrees(lat, longitude, 5e3);
        SimulationUtils.customFlyToFly(flightSimApp, destPos, TRAVEL_TIME);
        SimulationUtils.schedule(SimulationUtils.execServ, () -> new DepartAction(editorCore.getWorld().getSelectionModel(),
                editorCore.getDataTracker()).actionPerformed(null), TRAVEL_TIME + 1000);
    }


    @Override
    public void windowStateChanged(Position userPosition) {
        if (aircraft3D == null) {
            loadPlane.setEnabled(SimulationUtils.calculateEyeDist(orbitView.getCurrentEyePosition(),
                    Position.fromDegrees(og.latitude, og.longitude, 0d)));
        }
        if (hangar3d == null) {
            loadHangar(SimulationUtils.calculateEyeDist(orbitView.getCurrentEyePosition(),
                    Position.fromDegrees(og.latitude, og.longitude, 0d)));
        }
    }
}
