package nl.rug.oop.flaps.aircraft_editor.model;
import lombok.Getter;
import lombok.SneakyThrows;
import nl.rug.oop.flaps.aircraft_editor.controller.actions.BlueprintSelectionListener;
import nl.rug.oop.flaps.simulation.model.aircraft.CargoArea;
import nl.rug.oop.flaps.simulation.model.aircraft.Compartment;
import nl.rug.oop.flaps.simulation.model.aircraft.FuelTank;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public class BlueprintSelectionModel {
    private Compartment compartment = null;
    private final Set<BlueprintSelectionListener> listenerSet;
    private HashMap<String, Set<BlueprintSelectionListener>> listenerMap;
    public BlueprintSelectionModel() {
        listenerSet = new HashSet<>();
        listenerMap = new HashMap<>();
    }

    public void addListener(String identity, BlueprintSelectionListener listener) {
        if(!listenerMap.containsKey(identity)) {
            Set<BlueprintSelectionListener> set = new HashSet<>();
            set.add(listener);
            listenerMap.put(identity,set);
        } else {
            listenerMap.get(identity).add(listener);
        }
    }

    @SneakyThrows
    public void setSelectedCompartment(String areaId, Compartment area) {
        this.compartment = area;
        this.listenerMap.get(EditorCore.generalListenerID).forEach(listener -> {
            listener.compartmentSelected(area);
        });
        this.listenerMap.get(areaId).forEach(listener -> {
            listener.compartmentSelected(area);
        });
    }
}
