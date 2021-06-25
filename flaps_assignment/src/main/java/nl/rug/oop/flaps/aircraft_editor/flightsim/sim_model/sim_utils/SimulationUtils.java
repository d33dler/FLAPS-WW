package nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.sim_utils;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.OrbitView;
import nl.rug.oop.flaps.aircraft_editor.flightsim.sim_model.FlightSimApplication;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SimulationUtils {
    private static final ScheduledExecutorService execServ = new ScheduledThreadPoolExecutor(1);

    private static final long S = 1000;
    public static double getElevation(Globe planet, double lat, double lon) {
        return planet.getElevationModel().getElevation(Angle.fromDegrees(lat),
                Angle.fromDegrees(lon));
    }

    public static void animatedPanBack(BasicOrbitView orbitView, FlightSimApplication flightapp, double amount) {
        IntStream.range(0, 400).forEach(i -> zoomOut(orbitView, flightapp, amount));
    }

    public static void zoomOut(BasicOrbitView orbitView, FlightSimApplication flightApp, double amount) {
        orbitView.setZoom(ViewControlsSelectListener.computeNewZoom(orbitView, amount));
        flightApp.getWwjPanel().getWwd().redrawNow();
    }

    public static void animateAircraftFocus(BasicOrbitView view, ObjRenderable aircraft3D) {
        Position rawPos = aircraft3D.getPosition();
        Position improvedPos = Position.fromDegrees(rawPos.latitude.degrees, rawPos.longitude.degrees, rawPos.getElevation() + 100);
        schedule(execServ, () -> view.addEyePositionAnimator(2*S, view.getCurrentEyePosition(), improvedPos), S);
        schedule(execServ, () -> view.addPanToAnimator(rawPos, Angle.POS180, Angle.POS90, view.getZoom() + 10), 3*S);
        schedule(execServ, () -> view.addZoomAnimator(view.getZoom(), view.getZoom() + 5), 6*S);
    }

    public static void zoomIn(OrbitView orbitView, FlightSimApplication flightApp, double amount) {
        orbitView.setZoom(ViewControlsSelectListener.computeNewZoom(orbitView, amount));
        flightApp.getWwjPanel().getWwd().redrawNow();
    }

    public static void moveWithDelay(BasicOrbitView view, Position future, long delay) {
        schedule(execServ, () -> view.addEyePositionAnimator(4*S, view.getCurrentEyePosition(),
                Position.fromDegrees(future.latitude.degrees,
                        future.longitude.degrees, future.elevation)), delay);
        schedule(execServ, () -> view.addZoomAnimator(view.getZoom(), view.getZoom() - 10), delay + S);
    }

    public static void schedule(ScheduledExecutorService execServ, final Runnable r, long delay) {
        execServ.schedule(r, delay, TimeUnit.MILLISECONDS);
    }
}
