package nl.rug.oop.flaps.aircraft_editor.model;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.Configurator;
import nl.rug.oop.flaps.aircraft_editor.model.undomodel.UndoRedoManager;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.EditorFrame;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;
import nl.rug.oop.flaps.simulation.model.world.World;

/**
 * EditorCore class -  the core of the simulator; Stores the listenerIds,
 * initializes the configurator, aircraftDataTracker and other important model object classes;
 */
@Getter
@Setter
public class EditorCore {
    private World world;
    private Aircraft aircraft;
    private final BlueprintSelectionModel bpSelectionModel;
    private AircraftLoadingModel aircraftLoadingModel;
    private Remapper remapper;
    private UndoRedoManager undoRedoManager;
    private EditorFrame editorFrame;
    private Configurator configurator;
    private DatabaseBuilder databaseBuilder;
    private AircraftDataTracker dataTracker;
    private GeographicCoordinates originCoordinates;
    private Airport source;
    private Airport destination;


    private static final double NEARBY_UNIT_RANGE = 250.0;
    public static final String generalListenerID = "000AREA_abs";
    public static final String cargoListenerID = "100CARGO_ml";
    public static final String fuelListenerID = "100FUEL_ml";
    public static final String engineListenerID = "200ENGINE_ml";
    public static final String passengerListenerID = "300PASS_ml";
    public static final String povListenerID = "000POV_mv";

    public EditorCore(Aircraft aircraft, BlueprintSelectionModel bpSelectionModel, EditorFrame editorFrame) {
        this.world = aircraft.getWorld();
        this.aircraft = aircraft;
        this.editorFrame = editorFrame;
        this.bpSelectionModel = bpSelectionModel;
        this.bpSelectionModel.setEditorCore(this);
        this.databaseBuilder = new DatabaseBuilder();
        init();
    }

    /**
     * Initialize Configurator,UndoRedoManager,AircraftDataTracker;
     * add editorCore as listener to the AircraftLoadingModel class object;
     */
    private void init() {
        setupRemapper();
        getRoute();
        initRemap();
        this.aircraftLoadingModel = editorFrame.getAircraftLoadingModel();
        this.dataTracker = new AircraftDataTracker(this, aircraft);
        this.bpSelectionModel.setDataTracker(dataTracker);
        this.aircraftLoadingModel.setDataTracker(dataTracker);
        this.aircraftLoadingModel.setEditorCore(this);
        this.undoRedoManager = new UndoRedoManager(this);
        this.configurator = new Configurator(this);
        undoRedoManager.setConfigurator(configurator);
    }

    /**
     * Setup the remapper and add it to the blueprintSelectionModel
     */
    private void setupRemapper() {
        this.remapper = new Remapper(this,bpSelectionModel);
        bpSelectionModel.setRemapper(remapper);
        bpSelectionModel.setAreasMap(remapper.getAreasMap());
    }

    /**
     * Collect the travel route details for the aircraft;
     */
    private void getRoute() {
        this.source = world.getSelectionModel().getSelectedAirport();
        this.destination = world.getSelectionModel().getSelectedDestinationAirport();
        if ((destination != null) && destination.canAcceptIncomingAircraft()) {
            this.originCoordinates = world.getSelectionModel().getSelectedAirport().getGeographicCoordinates();
        }
    }

    /**
     * init remapping methods for all compartment coordinates
     */
    private void initRemap() {
        remapper.updateCompartmentCoords();
        remapper.listToCoordsMap(this.aircraft.getType().getCargoAreas());
        remapper.listToCoordsMap(this.aircraft.getType().getFuelTanks());
        remapper.listToCoordsMap(this.aircraft.getType().getEngines());
        remapper.listToCoordsMap(this.aircraft.getType().getCabin());
        remapper.setMapBoundaries();
    }

}

