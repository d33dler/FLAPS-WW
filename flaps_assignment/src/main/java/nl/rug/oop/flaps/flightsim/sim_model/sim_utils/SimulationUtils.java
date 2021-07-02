package nl.rug.oop.flaps.flightsim.sim_model.sim_utils;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.LatLon;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.osm.map.worldwind.gl.obj.ObjRenderable;
import gov.nasa.worldwind.view.BasicView;
import gov.nasa.worldwind.view.firstperson.BasicFlyView;
import gov.nasa.worldwind.view.firstperson.FlyToFlyViewAnimator;
import gov.nasa.worldwind.view.orbit.BasicOrbitView;
import gov.nasa.worldwind.view.orbit.OrbitView;
import nl.rug.oop.flaps.flightsim.sim_model.FlightSimApplication;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SimulationUtils {
    public static final double LAT_THOLD = 0.14, LONG_THOLD = 0.14, ELEV_THOLD = 5e3;
    public static final ScheduledExecutorService execServ = new ScheduledThreadPoolExecutor(1);

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
        flightApp.getWorldPanel().getWwd().redrawNow();
    }

    public static void animateAircraftFocus(BasicOrbitView view, ObjRenderable aircraft3D) {
        Position rawPos = aircraft3D.getPosition();
        Position improvedPos = Position.fromDegrees(rawPos.latitude.degrees, rawPos.longitude.degrees, rawPos.getElevation() + 100);
        schedule(execServ, () -> view.addEyePositionAnimator(2 * S, view.getCurrentEyePosition(), improvedPos), S);
        schedule(execServ, () -> view.addPanToAnimator(rawPos, Angle.POS180, Angle.POS90, view.getZoom() + 10), 3 * S);
        schedule(execServ, () -> view.addZoomAnimator(view.getZoom(), view.getZoom() + 5), 6 * S);
    }

    public static void zoomIn(OrbitView orbitView, FlightSimApplication flightApp, double amount) {
        orbitView.setZoom(ViewControlsSelectListener.computeNewZoom(orbitView, amount));
        flightApp.getWorldPanel().getWwd().redrawNow();
    }

    public static void customFlyToFly(FlightSimApplication simApp, Position location, long flightTime) {
        WorldWindow ww = simApp.getWorldWindow();
        BasicView defaultView = simApp.getOrbitView();
        defaultView.setPitch(Angle.NEG90);
        ww.redraw();
        BasicFlyView tempFly = simApp.getBasicFlyView();
        tempFly.setFarClipDistance(5000);
        tempFly.getViewPropertyLimits().setEyeElevationLimits(0, 1000000);
        ww.setView(tempFly);
        simApp.getCore().getSimRenderer().updateAircraftPosition();
        tempFly.setEyePosition(defaultView.getEyePosition());
        tempFly.setGlobe(ww.getModel().getGlobe());
        FlyToFlyViewAnimator animator =
                FlyToFlyViewAnimator.createFlyToFlyViewAnimator(tempFly,
                        defaultView.getEyePosition(),
                        new Position(new LatLon(location.latitude, location.longitude), location.elevation),
                        defaultView.getHeading(), Angle.POS90,
                        defaultView.getPitch(), Angle.NEG90,
                        defaultView.getEyePosition().getElevation(), location.elevation,
                        flightTime, WorldWind.RELATIVE_TO_GROUND);
        tempFly.addAnimator(animator);
        animator.start();
        tempFly.firePropertyChange(AVKey.VIEW, null, tempFly);

        schedule(execServ, () -> {
            defaultView.setEyePosition(tempFly.getCurrentEyePosition());
            ww.setView(defaultView);
            tempFly.setFarClipDistance(BasicView.MINIMUM_FAR_DISTANCE);
        }, flightTime + 1000);
    }

    public static void moveWithDelay(BasicOrbitView view, Position future, long delay) {
        schedule(execServ, () -> view.addEyePositionAnimator(4 * S, view.getCurrentEyePosition(),
                Position.fromDegrees(future.latitude.degrees,
                        future.longitude.degrees, future.elevation)), delay);
        schedule(execServ, () -> view.addZoomAnimator(view.getZoom(), view.getZoom() - 10), delay + S);
    }

    public static void schedule(ScheduledExecutorService execServ, final Runnable r, long delay) {
        execServ.schedule(r, delay, TimeUnit.MILLISECONDS);
    }

    public static boolean calculateEyeDist(Position current, Position focus) {
        double thLat = Math.abs(focus.latitude.degrees - current.latitude.degrees);
        double thLong = Math.abs(focus.longitude.degrees - current.longitude.degrees);
        double thElev = Math.abs(focus.getElevation() - current.getAltitude());
        return (thLat < LAT_THOLD && thLong < LONG_THOLD && thElev < ELEV_THOLD);
    }

    public static boolean calculateEyeAltitude(double altidude, Position pos) {
        double thElev = Math.abs(pos.getAltitude() - altidude);
        return (thElev < ELEV_THOLD);
    }

    public static void switchLayer(RenderableLayer layer, boolean enable, int opacity) {
        layer.setEnabled(enable);
        layer.setOpacity(opacity);
    }
}
