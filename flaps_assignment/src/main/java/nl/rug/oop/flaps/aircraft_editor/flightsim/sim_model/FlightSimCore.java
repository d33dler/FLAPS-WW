package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.globes.Globe;
import lombok.Getter;
import nl.rug.oop.flaps.aircraft_editor.flightsim.model_loaders.Models3DLoader;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view.FlightSimFrame;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_view.SimulatorWindow;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
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
    }


    private void init3dLoader() {
        this.models3DLoader = new Models3DLoader(worldWindow, earth, editorCore.getOriginCoordinates());
    }

}
