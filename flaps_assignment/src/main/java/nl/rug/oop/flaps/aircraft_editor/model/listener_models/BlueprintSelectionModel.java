package nl.rug.oop.flaps.aircraft_editor.model.listener_models;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;
import nl.rug.oop.flaps.aircraft_editor.model.Remapper;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.aircraft_editor.view.MessagesDb;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.b_print.BlueprintDisplay;
import nl.rug.oop.flaps.aircraft_editor.view.maineditor.main_panels.LogPanel;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.Compartment;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * BlueprintSelectionModel class - stores the list of update listeners to
 * blueprint compartment selections and fires updates;
 * Additionally, it identifies the closest compartment area to the users selection;
 */
@Log
@Getter
@Setter
public class BlueprintSelectionModel implements BlueprintSelectionListener {
    private EditorCore editorCore;
    private AircraftDataTracker dataTracker;
    private Compartment compartment = null;
    private Remapper remapper;


    /**
     * We use a hashmap for listener lists to separate listeners based on their roles & priority;
     */

    protected HashMap<String, List<BlueprintSelectionListener>> listenerMap;

    private NavigableMap<Double, NavigableMap<Double, Compartment>> areasMap;
    private LogPanel logPanel;
    private static final double CURSOR_TOLERANCE = BlueprintDisplay.MK_SIZE * 3;

    public BlueprintSelectionModel() {
        this.listenerMap = new HashMap<>();
    }

    /**
     * @param identity listenersId, to separate listeners when firing updates
     */

    public void addListener(String identity, BlueprintSelectionListener bpListener) {
        if (!listenerMap.containsKey(identity)) {
            List<BlueprintSelectionListener> list = new ArrayList<>();
            list.add(bpListener);
            listenerMap.put(identity, list);
        } else {
            listenerMap.get(identity).add(bpListener);
        };
    }


    /**
     * @param coords Cursor click coordinates on the JFrame
     * @return Optional: closest area to the coordinates - with tolerance
     * or Optional.empty();
     * We use nested NavigableMaps to capture efficiently the coordinates of the closest compartment area
     * relative to the users cursor's click coordinates;
     * Process description: we test the lower entry closest to x axis value - if null, we take the higher entry;
     * next, we test the lower entry closest to the y Axis value - if null, we take the higher entry and test tolerance;
     * otherwise we take the lower entry and test tolerance, returning Optional.empty() if test fails.
     */
    public Optional<Compartment> extractApproxArea(Point2D.Double coords) {
        NavigableMap<Double, Compartment> xAxis;
        try {
            if (areasMap.lowerEntry(coords.x) == null) {
                xAxis = areasMap.higherEntry(coords.x).getValue();
            } else {
                xAxis = areasMap.lowerEntry(coords.x).getValue();
            }
            if (xAxis != null) {
                if (xAxis.lowerEntry(coords.y) == null) {
                    return identifyArea(coords,
                            (xAxis.higherEntry(coords.y).getValue()));
                } else {
                    return identifyArea(coords,
                            (xAxis.lowerEntry(coords.y).getValue()));
                }
            } else {
                return Optional.empty();
            }
        } catch (NullPointerException e) {
            logPanel.updateLog(MessagesDb.BP_CURSOR_OUT);
            return Optional.empty();
        }
    }

    /**
     * @param coords      Cursor click coordinates;
     * @param compartment Identified compartment closest to the cursor's click coordinates;
     * @return area - if decalage is within tolerance limit, otherwise Optional.empty;
     */
    private Optional<Compartment> identifyArea(Point2D.Double coords, Compartment compartment) {
        if (compartment != null) {
            Point2D.Double closestAreaXY = editorCore.getRemapper().getLocalCoords().get(compartment.hashCode());
            double areaxy = closestAreaXY.getX() + closestAreaXY.getY();
            double cursorCoords = coords.x + coords.y;
            if (Math.abs(areaxy - cursorCoords) <= CURSOR_TOLERANCE) {
                return Optional.of(compartment);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * @param areaId id of the compartment area type
     * @param area   area subjected to changes
     *               Fires updates with priority for generalListeners;
     *               and fires separate updates for listeners concerned with
     *               the particular area; (offers scalability opportunities)
     */
    @SneakyThrows
    public void fireSelectedAreaUpdate(String areaId, Compartment area) {
        this.compartment = area;
        fireBpUpdate(area, dataTracker);
        this.listenerMap.get(EditorCore.generalListenerID).forEach(listener -> {
            listener.fireBpUpdate(area, dataTracker);
        });
        if (listenerMap.containsKey(areaId)) {
            listenerMap.get(areaId).forEach(listener -> {
                listener.fireBpUpdate(area, dataTracker);
            });
        }
    }

    @Override
    public void fireBpUpdate(Compartment area, AircraftDataTracker dataTracker) {
        BlueprintSelectionListener.super.fireBpUpdate(area, dataTracker);
    }
}