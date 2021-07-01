package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.ScreenImage;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwindx.examples.ApplicationTemplate;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

@Getter
public class SimulatorWindow extends JPanel implements SimWindowCallbackListener {
    private FlightSimCore core;
    private FlightSimFrame flightSimFrame;
    private FlightSimApplication flightSimApp;
    private EditorCore editorCore;
    private WorldWindow worldWindow;
    private BasicOrbitView orbitView;
    private ApplicationTemplate.AppPanel mapPanel;
    private Globe earth;
    private GeographicCoordinates og, dest;

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
        this.mapPanel = flightSimApp.init();
        this.worldWindow = mapPanel.getWwd();
        this.orbitView = (BasicOrbitView) worldWindow.getView();
        orbitView.simWindowChangeModel.addListener(this);
        mapPanel.setSize(new Dimension(1920, 1080));
        this.earth = worldWindow.getModel().getGlobe();
        RenderableLayer layer = new RenderableLayer();
        layer.setName("Cinematic mode");
        customizeLayer(new Point(700, 0),layer,"images", "flight_sim", "blackbar.png");
        customizeLayer(new Point(700, 975), layer,"images", "flight_sim", "blackbar.png");
        addLayer(layer);
        add(mapPanel);
    }

    @SneakyThrows
    private void customizeLayer(Point point, RenderableLayer layer, String path, String path2, String layerFile) {
        ScreenImage screenImage = new ScreenImage();
        screenImage.setImageSource(ImageIO.read(Path.of(path, path2, layerFile).toFile()));
        screenImage.setScreenLocation(point);
        layer.addRenderable(screenImage);
    }

    private void addLayer(RenderableLayer layer) {
        worldWindow.getModel().getLayers().add(layer);
    }

    @Override
    public void windowStateChanged(Position currentEyePos) {
        //TODO
    }
}
