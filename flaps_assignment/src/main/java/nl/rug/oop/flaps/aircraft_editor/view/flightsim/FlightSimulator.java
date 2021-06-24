package nl.rug.oop.flaps.aircraft_editor.view.flightsim;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Ellipsoid;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import gov.nasa.worldwindx.examples.ViewIteration;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.nio.file.Path;

@Getter
public class FlightSimulator extends JFrame {

    private final ViewIteration viewIteration;
    private final EditorCore editorCore;
    private final Airport origin;
    private final Airport destination;
    private BasicOrbitView orbitView;
    private JPanel mapPanel;
    private GeographicCoordinates og, dest;
    private Image aircraftImg;
    private Ellipsoid ellipsoid;
    public FlightSimulator(EditorCore editorCore) throws HeadlessException {
        setTitle("FlightSim");
        this.editorCore = editorCore;
        this.viewIteration = new ViewIteration();
        this.origin = editorCore.getSource();
        this.destination = editorCore.getDestination();
        init();
    }

    @SneakyThrows
    private void init() {
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(1400, 1200));
        addWorldMap();
        addAircraft();
        addButtons();
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }

    @SneakyThrows
    private void initSimPanel() {
        this.aircraftImg = ImageIO.read(Path.of("images", "flight_sim", ("model_plane.png")).toFile()).
            getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        this.mapPanel = viewIteration.init(aircraftImg);
        mapPanel.setPreferredSize(new Dimension(1024, 768));
    }


    private void addButtons() {
        JButton origins = new JButton("origin");
        origins.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ellipsoid.setCenterPosition(Position.fromDegrees(og.getLatitude(), og.getLongitude(), 1000));
                travel(og.getLatitude(), og.getLongitude());
            }
        });
        JButton arrival = new JButton("destination");
        arrival.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ellipsoid.setCenterPosition(Position.fromDegrees(dest.getLatitude(), dest.getLongitude(), 1000));
                travel(dest.getLatitude(), dest.getLongitude());
            }
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
        buttonsPanel.add(origins);
        buttonsPanel.add(arrival);
        add(buttonsPanel, BorderLayout.WEST);
    }

    private void addWorldMap() {
        initSimPanel();
        getContentPane().add(mapPanel, BorderLayout.CENTER);
        setPathStart();
    }

    private void addAircraft() {
        RenderableLayer layer = new RenderableLayer();
        layer.setName("Aircraft");
        ShapeAttributes attrs2 = new BasicShapeAttributes();
        attrs2.setInteriorMaterial(Material.PINK);
        attrs2.setInteriorOpacity(1);
        attrs2.setEnableLighting(true);
        attrs2.setOutlineMaterial(Material.WHITE);
        attrs2.setOutlineWidth(2d);
        attrs2.setDrawOutline(false);
        ellipsoid = new Ellipsoid(Position.fromDegrees(og.getLatitude(), og.getLongitude(), 10), 100, 100, 30,
                Angle.fromDegrees(0), Angle.fromDegrees(0), Angle.fromDegrees(90));
        ellipsoid.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        ellipsoid.setImageSources("images/flight_sim/ufo_texture.jpg");
        ellipsoid.setAttributes(attrs2);
        ellipsoid.setValue(AVKey.DISPLAY_NAME, editorCore.getAircraft().getType().getName());
        layer.addRenderable(ellipsoid);
        ApplicationTemplate.insertBeforePlacenames(viewIteration.getWwjPanel().getWwd(), layer);
    }

    private void setPathStart() {
        this.og = origin.getGeographicCoordinates();
        this.dest = destination.getGeographicCoordinates();
        double lat = og.getLatitude();
        double longitude = og.getLongitude();
        this.orbitView = (BasicOrbitView) viewIteration.getWwjPanel().getWwd().getView();
        orbitView.setHeading(Angle.fromDegrees(90));
        if (orbitView.getEyePosition() == null) {
            orbitView.setEyePosition(Position.ZERO);
        }
        orbitView.addEyePositionAnimator(4000, Position.fromDegrees(0, 30, 1e8), Position.fromDegrees(lat, longitude, 8e4));
    }

    @SneakyThrows
    private void travel(double lat, double longitude) {
        zoomOut();
        orbitView.setZoom(ViewControlsSelectListener.computeNewZoom(orbitView, 15));
        orbitView.addEyePositionAnimator(4000, orbitView.getCurrentEyePosition(),
                Position.fromDegrees(lat, longitude, 8e4));
        orbitView.setZoom(ViewControlsSelectListener.computeNewZoom(orbitView, -15));
    }

    private void zoomOut() {
        Position prevPos = orbitView.getCurrentEyePosition();
        Position newPos = Position.fromDegrees(prevPos.getLatitude().getDegrees() + 5, prevPos.getLongitude().getDegrees() + 5,
                prevPos.getElevation() * 100);
        orbitView.addEyePositionAnimator(4000, orbitView.getCurrentEyePosition(), newPos);

    }

}
