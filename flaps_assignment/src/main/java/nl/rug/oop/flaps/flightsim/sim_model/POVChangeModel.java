package nl.rug.oop.flaps.flightsim.sim_model;

import gov.nasa.worldwind.geom.Position;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.flightsim.sim_controller.sim_listeners.POVChangeListeners;
import nl.rug.oop.flaps.aircraft_editor.model.EditorCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class POVChangeModel implements POVChangeListeners
{
    private EditorCore editorCore;
    private HashMap<String, List<POVChangeListeners>> listenerMap = new HashMap<>();
    private Position povPosition = null;

    public POVChangeModel() {
    }

    public void addListener(String identity, POVChangeListeners povListener) {
        if (!listenerMap.containsKey(identity)) {
            List<POVChangeListeners> list = new ArrayList<>();
            list.add(povListener);
            listenerMap.put(identity, list);
        } else listenerMap.get(identity).add(povListener);
    }

    public void fireChangedPOVUpdate(String listenerId, Position position) {
        this.povPosition = position;
        listenerMap.get(listenerId).forEach(listener -> {
            listener.firePOVChangeUpdate(povPosition);
        });
    }

    @Override
    public void firePOVChangeUpdate(Position currentEyePos) {
    }
}
