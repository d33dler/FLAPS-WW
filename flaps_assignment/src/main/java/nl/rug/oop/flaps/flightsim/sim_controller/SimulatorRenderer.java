package nl.rug.oop.flaps.flightsim.sim_controller;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.flaps_interfaces.SimWindowCallbackListener;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.firstperson.BasicFlyView;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimCore;
import nl.rug.oop.flaps.flightsim.sim_model.sim_utils.SimulationUtils;
import nl.rug.oop.flaps.flightsim.sim_view.SimulatorWindow;

public class SimulatorRenderer implements SimWindowCallbackListener {
    private FlightSimCore core;
    private SimulatorWindow simulatorWindow;
    private ObjRenderable aircraft_3d;
    private BasicFlyView flyView;
    private WorldWindow worldWindow;

    public SimulatorRenderer(FlightSimCore core, SimulatorWindow simulatorWindow, ObjRenderable aircraft_3d) {
        this.core = core;
        this.simulatorWindow = simulatorWindow;
        this.flyView = simulatorWindow.getBasicFlyView();
        this.aircraft_3d = aircraft_3d;
        this.worldWindow = simulatorWindow.getWorldWindow();
        // simulatorWindow.getOrbitView().simWindowChangeModel.addListener(this);
    }

    @Override
    public void windowStateChanged(Position pos) {

    }

    /**
     * It was planned that the aircraft moves synchronously below the scripted camera,
     * but something causes the plane to not display: e.g. clipping distance (reconfiguration didn't work)
     */
    public void updateAircraftPosition() {
        aircraft_3d.setSize(40);
        new Thread(() -> {
            while (flyView.isAnimating()) {
                Position p = flyView.getCurrentEyePosition();
                Position a = new Position(p.latitude, p.longitude, SimulationUtils.getElevation(flyView.getGlobe(), p.latitude.degrees, p.longitude.degrees));
                aircraft_3d.setPosition(a);
            }
            aircraft_3d.setSize(10);
        }).start();
    }
}
