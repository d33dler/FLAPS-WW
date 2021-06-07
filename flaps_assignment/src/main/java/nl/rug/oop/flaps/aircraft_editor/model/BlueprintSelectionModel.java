package nl.rug.oop.flaps.aircraft_editor.model;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.AircraftDataTracker;
import nl.rug.oop.flaps.aircraft_editor.model.listeners.interfaces.BlueprintSelectionListener;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;

import java.util.*;

@Getter
@Setter
public class BlueprintSelectionModel {
    private EditorCore editorCore;
    private AircraftDataTracker dataTracker;
    private Compartment compartment = null;
    private HashMap<String, List<BlueprintSelectionListener>> listenerMap;

    public BlueprintSelectionModel() {
        this.listenerMap = new HashMap<>();
    }

    public void addListener(String identity, BlueprintSelectionListener listener) {
        if(!listenerMap.containsKey(identity)) {
            List<BlueprintSelectionListener> list = new ArrayList<>();
            list.add(listener);
            listenerMap.put(identity,list);
        } else {
            listenerMap.get(identity).add(listener);
        }
    }

    @SneakyThrows
    public void fireSelectedAreaUpdate(String areaId, Compartment area) {
        this.compartment = area;
        this.listenerMap.get(EditorCore.generalListenerID).forEach(listener -> {
            listener.compartmentSelected(area, dataTracker);
        });
        this.listenerMap.get(areaId).forEach(listener -> {
            listener.compartmentSelected(area, dataTracker);
        });
    }
}
