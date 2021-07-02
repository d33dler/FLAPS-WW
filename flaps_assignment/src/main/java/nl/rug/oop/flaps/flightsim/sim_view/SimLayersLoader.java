package nl.rug.oop.flaps.flightsim.sim_view;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.*;
import gov.nasa.worldwindx.examples.util.PowerOfTwoPaddedImage;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


/**
 * MarkersLoader loads the letter box, travel path, annotation layers,[...] to display in the world.
 */
@Getter
public class SimLayersLoader implements SimWindowCallbackListener {
    private FlightSimCore core;
    private SimulatorWindow simulatorWindow;
    private WorldWindow worldWindow;

    private ArrayList<Position> pathPositions = new ArrayList<>();
    private GeographicCoordinates og, dest;
    private PowerOfTwoPaddedImage ORIGIN_BANNER, DESTINATION_BANNER;
    private List<RenderableLayer> simLayers = new ArrayList<>();
    private List<RenderableLayer> dynamicSimLayers = new ArrayList<>();
    public SimLayersLoader(FlightSimCore core, SimulatorWindow simulatorWindow) {
        this.core = core;
        this.simulatorWindow = simulatorWindow;
        this.worldWindow = simulatorWindow.getWorldWindow();
        this.og = simulatorWindow.og;
        this.dest = simulatorWindow.dest;
        simulatorWindow.getOrbitView().simWindowChangeModel.addListener(this);
        init();
    }

    private void init() {
        generateLetterBox(new RenderableLayer());
        generateTravelPaths(new RenderableLayer());
        generateBanners();
        generateAnnotations(new RenderableLayer());
    }

    private void generateBanners() {
        this.ORIGIN_BANNER = PowerOfTwoPaddedImage.fromBufferedImage((BufferedImage) core.origin.getBannerImage());
        this.DESTINATION_BANNER = PowerOfTwoPaddedImage.fromBufferedImage((BufferedImage) core.destination.getBannerImage());
    }

    private void generateLetterBox(RenderableLayer layer) {
        layer.setName("Cinematic mode");
        customizeLayer(new Point(700, 0), layer, "images", "flight_sim", "blackbar.png");
        customizeLayer(new Point(700, 975), layer, "images", "flight_sim", "blackbar.png");
        addLayer(layer,false);
    }

    private void generateTravelPaths(RenderableLayer layer) {
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(Color.YELLOW));
        attrs.setOutlineWidth(3d);
        attrs.setOutlineOpacity(0.8);
        attrs.setEnableAntialiasing(true);
        pathPositions = new ArrayList<>();
        pathPositions.add(Position.fromDegrees(og.latitude, og.longitude, 0d));
        pathPositions.add(Position.fromDegrees(og.latitude + 0.1, og.longitude + 0.1, 6e3));
        pathPositions.add(Position.fromDegrees(dest.latitude - 0.1, dest.longitude - 0.1, 6e3));
        pathPositions.add(Position.fromDegrees(dest.latitude, dest.longitude, 0d));
        gov.nasa.worldwind.render.Path travelPath = new gov.nasa.worldwind.render.Path(pathPositions);
        travelPath.setFollowTerrain(true);
        travelPath.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        travelPath.setPathType(AVKey.GREAT_CIRCLE);
        travelPath.setAttributes(attrs);
        layer.addRenderable(travelPath);
        addLayer(layer,false);
    }

    @SneakyThrows
    private void customizeLayer(Point point, RenderableLayer layer, String path, String path2, String layerFile) {
        ScreenImage screenImage = new ScreenImage();
        screenImage.setImageSource(ImageIO.read(Path.of(path, path2, layerFile).toFile()));
        screenImage.setScreenLocation(point);
        layer.addRenderable(screenImage);
    }

    private void generateAnnotations(RenderableLayer layer) {
        GlobeAnnotation annotation_og, annotation_dest;
        layer.setName("Checkpoints");
        Position og_annPos = Position.fromDegrees(core.og.latitude + 0.01, core.og.longitude + 0.01, 0d);
        Position dest_annPos = Position.fromDegrees(core.dest.latitude + 0.01, core.dest.longitude + 0.01, 0d);
        annotation_og = new GlobeAnnotation(core.origin.getName(), og_annPos);
        annotation_dest = new GlobeAnnotation(core.destination.getName(), dest_annPos);
        configAttributes(ORIGIN_BANNER, annotation_og.getAttributes());
        configAttributes(DESTINATION_BANNER, annotation_dest.getAttributes());
        layer.addRenderable(annotation_og);
        layer.addRenderable(annotation_dest);

        addLayer(layer,true);
    }

    private void configAttributes(PowerOfTwoPaddedImage image, AnnotationAttributes attr) {
        int inset = 10; // pixels
        attr.setInsets(new Insets(image.getOriginalHeight() + inset * 2, inset, inset, inset));
        attr.setImageSource(image.getPowerOfTwoImage());
        attr.setImageOffset(new Point(inset, inset));
        attr.setImageRepeat(AVKey.REPEAT_NONE);
        attr.setImageOpacity(1);
        attr.setSize(new Dimension(image.getOriginalWidth() + inset * 2, 0));
        attr.setDistanceMinScale(.20);
        attr.setDistanceMaxScale(.25);
        attr.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
        attr.setAdjustWidthToText(AVKey.SIZE_FIXED);
        attr.setBackgroundColor(Color.WHITE);
        attr.setTextColor(Color.BLACK);
        attr.setBorderColor(Color.BLACK);
    }


    private void addLayer(RenderableLayer layer, boolean dynamic) {
        layer.setOpacity(0);
        simLayers.add(layer);
        if(dynamic) {
            dynamicSimLayers.add(layer);
        }
        worldWindow.getModel().getLayers().add(layer);
    }


    @Override
    public void windowStateChanged(Position userPosition) {
        if (SimulationUtils.calculateEyeAltitude(0d, userPosition))
            dynamicSimLayers.forEach(layer -> SimulationUtils.switchLayer(layer, true, 1));
        else dynamicSimLayers.forEach(layer -> SimulationUtils.switchLayer(layer, false, 0));
    }

}
