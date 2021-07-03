package nl.rug.oop.flaps.flightsim.sim_model;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.flightsim.model_loaders.Models3DLoader;
import nl.rug.oop.flaps.flightsim.sim_controller.SimulatorRenderer;
import nl.rug.oop.flaps.flightsim.sim_view.FlightSimFrame;
import nl.rug.oop.flaps.flightsim.sim_view.SimulatorWindow;
import nl.rug.oop.flaps.simulation.model.airport.Airport;
import nl.rug.oop.flaps.simulation.model.map.coordinates.GeographicCoordinates;

@Getter
public class FlightSimCore {
    private EditorCore editorCore;
    private Models3DLoader models3DLoader;
    private FlightSimFrame flightSimFrame;
    private SimulatorWindow simulatorWindow;
    private WorldWindow worldWindow;
    private Globe earth;
    public GeographicCoordinates og, dest;
    public Airport origin;
    public Airport destination;
    private FlightSimApplication simApp;
    public Position ogPos, destPos;
    private SimulatorRenderer simRenderer;
    public FlightSimCore(EditorCore editorCore,FlightSimApplication simApp, FlightSimFrame flightSimFrame) {
        this.editorCore = editorCore;
        this.flightSimFrame = flightSimFrame;
        this.simApp = simApp;
        init();
    }

    private void init() {
        initAirports();
        initSimWindow();
        init3dLoader();
        initRenderer();
    }

    private void initSimWindow() {
        this.simulatorWindow = new SimulatorWindow(this, flightSimFrame, editorCore);
        this.worldWindow = simulatorWindow.getWorldWindow();
        this.earth = simulatorWindow.getEarth();
    }

    private void initAirports() {
        this.origin = editorCore.getSource();
        this.destination = editorCore.getDestination();
        this.og = origin.getGeographicCoordinates();
        this.dest = destination.getGeographicCoordinates();
        ogPos = Position.fromDegrees(og.latitude,og.longitude,0d);
        destPos = Position.fromDegrees(dest.latitude,dest.longitude,0d);
    }


    private void init3dLoader() {
        this.models3DLoader = new Models3DLoader(worldWindow, earth, editorCore.getOriginCoordinates());
    }
    private void initRenderer() {
        this.simRenderer = new SimulatorRenderer(this,simulatorWindow, models3DLoader.getAircraft3d_obj());
    }

}
