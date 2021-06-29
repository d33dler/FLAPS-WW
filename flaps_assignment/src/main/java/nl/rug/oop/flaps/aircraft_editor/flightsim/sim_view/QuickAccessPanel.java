package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view;

import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.POVChangeModel;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QuickAccessPanel extends JPanel implements SimWindowCallbackListener {
    private POVChangeModel povChangeModel;
    private FlightSimFrame flightSimFrame;
    private FlightSimApplication flightSimApp;
    private BasicOrbitView orbitView;
    private EditorCore editorCore;
    private JTextField rollField, azimuthField, elevateField;
    private ObjRenderable aircraft3D;
    private JPanel controlPanel;
    private Globe planet;
    private static final double HEIGHT_LIM = 8e4, ZOOMOUT_LIM = 90;
    private JButton loadPlane;
    private GeographicCoordinates og, dest;
    private static final double LAT_THOLD = 0.3, LONG_THOLD = 0.3, ELEV_THOLD = 3000;

    public QuickAccessPanel(FlightSimFrame flightSimFrame, BasicOrbitView orbitView, EditorCore editorCore) {
        this.flightSimFrame = flightSimFrame;
        this.orbitView = orbitView;
        this.editorCore = editorCore;
        this.planet = flightSimFrame.getEarth();
        this.flightSimApp = flightSimFrame.getFlightSimApp();
        orbitView.simWindowChangeModel.addListener(this);
        init();
    }

    private void init() {
        addButtons();
        initFields();
        addAircraftSwitches();
    }

    private void addButtons() {
        this.og =
                flightSimFrame.getOrigin().getGeographicCoordinates();
        this.dest =
                flightSimFrame.getDestination().getGeographicCoordinates();
        this.loadPlane = new JButton("Load Aircraft");
        loadPlane.setToolTipText("In order to load the plane you must be located near the origin airport");
        loadPlane.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadPlane.setEnabled(false);
                loadAircraft(og.getLatitude(), og.getLongitude());
            }
        });
        loadPlane.setEnabled(false);
        JButton origins = new JButton("origin");
        origins.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travel(og.getLatitude(), og.getLongitude());
            }
        });
        JButton arrival = new JButton("destination");
        arrival.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                travel(dest.getLatitude(), dest.getLongitude());
            }
        });
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
        controlPanel.add(origins);
        controlPanel.add(arrival);
        controlPanel.add(zoomIn);
        controlPanel.add(zoomOut);
        add(controlPanel, BorderLayout.WEST);
    }

    private void initFields() {
        JPanel textPanel = new JPanel();
        textPanel.setPreferredSize(new Dimension(200, 200));
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        this.rollField = new JTextField();
        rollField.setPreferredSize(new Dimension(200, 25));
        rollField.setBackground(new Color(111, 51, 51, 223));
        this.azimuthField = new JTextField();
        azimuthField.setColumns(10);
        azimuthField.setBackground(new Color(89, 123, 89, 216));
        this.elevateField = new JTextField();
        elevateField.setBackground(new Color(56, 89, 102, 216));
        elevateField.setColumns(10);
        textPanel.add(rollField);
        textPanel.add(azimuthField);
        textPanel.add(elevateField);
        controlPanel.add(textPanel);
    }

    private void addAircraftSwitches() {
        JButton azimuth = new JButton("Azimuth");
        azimuth.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aircraft3D.setAzimuth(Double.parseDouble(azimuthField.getText()));
                flightSimApp.getWwjPanel().getWwd().redrawNow();
            }
        });
        JButton roll = new JButton("Roll");
        roll.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aircraft3D.setRoll(Double.parseDouble(rollField.getText()));
                flightSimApp.getWwjPanel().getWwd().redrawNow();
            }
        });
        JButton elevate = new JButton("Elevate");
        elevate.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aircraft3D.setElevation(Double.parseDouble(elevateField.getText()));
                flightSimApp.getWwjPanel().getWwd().redrawNow();
            }
        });
        controlPanel.add(azimuth);
        controlPanel.add(roll);
        controlPanel.add(elevate);
    }

    private void loadAircraft(double lat, double lon) {
        aircraft3D = flightSimFrame.getAircraftObj_3d();
        double elevation = SimulationUtils.getElevation(planet, lat, lon);
        aircraft3D.setPosition(Position.fromDegrees(lat, lon, elevation));
        SimulationUtils.animatedPanBack(orbitView, flightSimApp,0.1);
        SimulationUtils.animateAircraftFocus(orbitView, aircraft3D);
    }

    @SneakyThrows
    private void travel(double lat, double longitude) {
        double elevation = SimulationUtils.getElevation(planet, lat, longitude);
        if (orbitView.getCurrentEyePosition().getAltitude() < HEIGHT_LIM)
            SimulationUtils.animatedPanBack(orbitView,flightSimApp, 0.2);
        SimulationUtils.moveWithDelay(orbitView, Position.fromDegrees(lat, longitude, elevation + 300), 2500);
    }


    @Override
    public void windowStateChanged(Position userPosition) {
        if (aircraft3D == null) {
            Position originPos = Position.fromDegrees(og.getLatitude(), og.getLongitude());
            Position current = orbitView.getCurrentEyePosition();
            double thLat = Math.abs(originPos.latitude.degrees - current.latitude.degrees);
            double thLong = Math.abs(originPos.longitude.degrees - current.longitude.degrees);
            double thElev = Math.abs(originPos.getElevation() - current.getAltitude());
            loadPlane.setEnabled(thLat < LAT_THOLD && thLong < LONG_THOLD && thElev < ELEV_THOLD);
        }
    }
}
