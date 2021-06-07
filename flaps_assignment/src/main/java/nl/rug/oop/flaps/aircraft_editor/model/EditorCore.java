package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.Configurator;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.CargoUnitsListener;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.FuelSupplyListener;
import nl.rug.oop.flaps.aircraft_editor.view.BlueprintDisplay;
import nl.rug.oop.flaps.aircraft_editor.view.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;
import nl.rug.oop.flaps.simulation.model.world.World;

import java.awt.geom.Point2D;
import java.util.*;

@Getter
@Setter
public class EditorCore implements CargoUnitsListener, BlueprintSelectionListener, FuelSupplyListener {
    private World world;
    private Aircraft aircraft;
    private static double AIRCRAFT_LEN;
    private final BlueprintSelectionModel selectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private EditorFrame editorFrame;
    private Configurator configurator;
    private CargoDatabase cargoDatabase;
    private AircraftDataTracker dataTracker;
    private GeographicCoordinates originCoordinates;
    private Airport source;
    private Airport destination;
    public final static Point2D.Double BP_POS = new Point2D.Double(0, 20);;
    public static double BP_WIDTH, BP_HEIGHT, BP_RATIO, BP_MARGIN;

    private NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap = new TreeMap<>();
    protected HashMap<Integer, Point2D.Double> localCoords = new HashMap<>();

    private static final double NEARBY_UNIT_RANGE = 250.0;
    public static final String generalListenerID = "000AREA_abs";
    public static final String cargoListenerID = "100CARGO_ml";
    public static final String fuelListenerID = "100FUEL_ml";


    public EditorCore(Aircraft aircraft, BlueprintSelectionModel selectionModel, EditorFrame editorFrame) {
        this.world = aircraft.getWorld();
        this.aircraft = aircraft;
        this.editorFrame = editorFrame;
        this.selectionModel = selectionModel;
        this.selectionModel.addListener(generalListenerID,this);
        this.selectionModel.setEditorCore(this);
        init();
    }

    private void init() {
        getRoute();
        configureBlueprintImg();
        this.aircraftLoadingModel = editorFrame.getAircraftLoadingModel();
        aircraftLoadingModel.addListener((CargoUnitsListener) this);
        aircraftLoadingModel.addListener((FuelSupplyListener) this);
        this.dataTracker = new AircraftDataTracker(this, aircraft);
        this.selectionModel.setDataTracker(dataTracker);
        this.aircraftLoadingModel.setDataTracker(dataTracker);
        this.configurator = new Configurator(this);
        updateCompartmentCoords();
        listToCoordsMap(this.aircraft.getType().getCargoAreas());
        listToCoordsMap(this.aircraft.getType().getFuelTanks());
    }

    private void getRoute() {
        this.source = world.getSelectionModel().getSelectedAirport();
        this.destination = world.getSelectionModel().getSelectedDestinationAirport();
        //  if ((destination != null) && destination.canAcceptIncomingAircraft()) {
        this.originCoordinates = world.getSelectionModel().getSelectedAirport().getGeographicCoordinates();
        //      } else {
//TODO
        //     }
    }

    public Optional<Compartment> extractApproxArea(Point2D.Double coords) {
        NavigableMap<Double, Compartment> xAxis;
        try {
            if (this.areasMap.lowerEntry(coords.x).getValue() == null) {
                xAxis = this.areasMap.higherEntry(coords.x).getValue();
            } else {
                xAxis = this.areasMap.lowerEntry(coords.x).getValue();
            }
            if (xAxis.lowerEntry(coords.y).getValue() == null) {
                return Optional.ofNullable(xAxis.higherEntry(coords.y).getValue());
            } else {
                return Optional.ofNullable(xAxis.lowerEntry(coords.y).getValue());
            }
        } catch (NullPointerException e) {
            System.out.println("Your cursor is outside of the blueprint's coordinate area range.");
            return Optional.empty();
        }
    }

    private void configureBlueprintImg() {
        AIRCRAFT_LEN = aircraft.getType().getLength();
        BP_MARGIN = 30;
        BP_WIDTH = aircraft.getType().getBlueprintImage().getWidth(editorFrame);
        BP_HEIGHT = aircraft.getType().getBlueprintImage().getHeight(editorFrame);
        BP_RATIO = BP_HEIGHT / BP_WIDTH;
        BP_HEIGHT = 500;
        BP_WIDTH = 500 / BP_RATIO;
    }

    private void listToCoordsMap(List<? extends Compartment> list) {
        list.forEach(area -> {
            Point2D.Double pos = this.localCoords.get(area.hashCode());
            if (this.areasMap.containsKey(pos.x)) {
                this.areasMap.get(pos.x).put(pos.y, area);
            } else {
                NavigableMap<Double, Compartment> mapY = new TreeMap<>();
                mapY.put(pos.y, area);
                this.areasMap.put(pos.x, mapY);
            }
        });
    }

    private void updateCompartmentCoords() {
        updateXY(this.aircraft.getType().getCargoAreas());
        updateXY(this.aircraft.getType().getFuelTanks());
    }

    private void updateXY(List<? extends Compartment> units) {
        units.forEach(area -> {
            var p = remap(area.getCoords());
            this.localCoords.put(area.hashCode(), p);
        });
    }

    public Point2D.Double remap(Point2D.Double source) {
        double bpSize = BP_WIDTH;
        double s = BlueprintDisplay.MK_SIZE;
        double x = BP_POS.x + (bpSize / 2) + (source.x * (bpSize / AIRCRAFT_LEN)) - s / 2;
        double y = BP_POS.y + (source.y * (bpSize / AIRCRAFT_LEN)) - s / 2;
        System.out.println("Res: "+ x + "  :  " + y);
        return new Point2D.Double(x, y);
    }

    @Override
    public void compartmentSelected(Compartment area, AircraftDataTracker dataTracker) { //TODO priority for EditorCore
        BlueprintSelectionListener.super.compartmentSelected(area, dataTracker);
    }

    @Override
    public void fireCargoTradeUpdate(Aircraft aircraft, AircraftDataTracker dataTracker) {
        CargoUnitsListener.super.fireCargoTradeUpdate(aircraft, dataTracker);
    }

    @Override
    public void fireFuelSupplyUpdate(Aircraft aircraft, AircraftDataTracker dataTracker) {
        FuelSupplyListener.super.fireFuelSupplyUpdate(aircraft, dataTracker);
    }
}

