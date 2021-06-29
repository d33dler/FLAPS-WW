package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.model.listener_models.BlueprintSelectionModel;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print.BlueprintDisplay;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * Remapper class - used to remap all aircraft compartment coordinates provided by the airplane type class
 * of the specific airplane;
 */
@Getter
@Setter
public class Remapper {

    private Aircraft aircraft;
    private EditorCore editorCore;
    private BlueprintSelectionModel bpSelectionModel;
    public static double AIRCRAFT_LEN, BP_WIDTH, BP_HEIGHT, BP_RATIO, Pixels_per_M;
    public final static Point2D.Double BP_POS = new Point2D.Double(0, 20);
    public static final double BP_MARGIN = 30, BP_OFF = 50;
    private static final double BP_DEF_H = 500;
    private static final double BP_DEF_W = 500;
    protected HashMap<Integer, Point2D.Double>
            localCoords = new HashMap<>(),
            blueprintCoords = new HashMap<>();

    private NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap = new TreeMap<>();
    private HashMap<Compartment, Point2D.Double> alternative = new HashMap<>();

    public Remapper(EditorCore editorCore, BlueprintSelectionModel bpSelectionModel) {
        this.editorCore = editorCore;
        this.bpSelectionModel = bpSelectionModel;
        this.aircraft = editorCore.getAircraft();
        configBlueprintParameters();
    }

    /**
     * Adapts the blueprint height,width,ratio according to the image values;
     * Computes pixels_per_meter;
     */
    public void configBlueprintParameters() {
        EditorFrame editorFrame = editorCore.getEditorFrame();
        AIRCRAFT_LEN = aircraft.getType().getLength();
        Pixels_per_M = BP_DEF_H / AIRCRAFT_LEN;
        BP_WIDTH = aircraft.getType().getBlueprintImage().getWidth(editorFrame);
        BP_HEIGHT = aircraft.getType().getBlueprintImage().getHeight(editorFrame);
        BP_RATIO = BP_HEIGHT / BP_WIDTH;
        BP_HEIGHT = BP_DEF_H;
        BP_WIDTH = BP_DEF_W / BP_RATIO;
    }

    /**
     * Updates XY coordinates for all cargoAreas and fuel tanks
     */
    public void updateCompartmentCoords() {
        updateXY(this.aircraft.getType().getCargoAreas(), null);
        updateXY(this.aircraft.getType().getFuelTanks(), null);
        updateXY(this.aircraft.getType().getEngines(), null);
        updateXY(this.aircraft.getType().getCabin(), remapCabins());
    }

    private HashMap<Compartment, Point2D.Double> remapCabins() {
        this.aircraft.getType().getCabin().forEach(cabin -> alternative.put(cabin, new Point2D.Double(
                Remapper.BP_WIDTH - Remapper.BP_OFF,
                Remapper.BP_HEIGHT - Remapper.BP_OFF)));
        return alternative;
    }

    private void updateXY(List<? extends Compartment> units, HashMap<Compartment, Point2D.Double> alternative) {
        units.forEach(area -> {
            var p = remap(area.getCoords());
            localCoords.put(area.hashCode(), p);
            if (alternative != null) blueprintCoords.put(area.hashCode(), alternative.get(area));
            else blueprintCoords.put(area.hashCode(), p);
        });
    }


    /**
     * @param source original coordinates of a specific compartment area
     * @return remapped coordinates based on aircraft length, indicator size, image display margins.
     */
    public Point2D.Double remap(Point2D.Double source) {
        double bpSize = BP_WIDTH;
        double s = BlueprintDisplay.MK_SIZE;
        double x = BP_POS.x + (bpSize / 2) + (source.x * (bpSize / AIRCRAFT_LEN)) - s / 2;
        double y = BP_POS.y + (source.y * (bpSize / AIRCRAFT_LEN)) - s / 2;
        return new Point2D.Double(x, y);
    }

    /**
     * @param list of compartments;
     *             fills the areas map of coordinates with the XY coordinates and the compartment area;
     */
    public void listToCoordsMap(List<? extends Compartment> list) {
        NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap = bpSelectionModel.getAreasMap();
        list.forEach(area -> {
            Point2D.Double pos = blueprintCoords.get(area.hashCode());
            if (areasMap.containsKey(pos.x)) {
                areasMap.get(pos.x).put(pos.y, area);
            } else {
                NavigableMap<Double, Compartment> mapY = new TreeMap<>();
                mapY.put(pos.y, area);
                areasMap.put(pos.x, mapY);
            }
        });
    }

    /**
     * Sets map coordinates margins (thus avoiding null-pointer exceptions during compartments location retrieval)
     */
    public void setMapBoundaries() {
        double margin = BP_MARGIN * 2;
        checkPutBound(areasMap, BP_WIDTH + margin);
        areasMap.values().forEach(navMap -> {
            if (navMap == null) {
                navMap = new TreeMap<>();
                navMap.put(BP_HEIGHT + margin, null);
            }
            checkPutBound(navMap, BP_HEIGHT + margin);
        });
    }

    private void checkPutBound(NavigableMap<Double, ?> navMap, double bound) {
        if (!navMap.containsKey(0.0)) {
            navMap.put(0.0, null);
        }
        if (!navMap.containsKey(bound)) {
            navMap.put(bound, null);
        }
    }
}
