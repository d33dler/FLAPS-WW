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
    private Map<Class<? extends Compartment>, Method > methodMap;
    public BlueprintSelectionModel() {
        listenerSet = new HashSet<>();
        methodMap = new HashMap<>();
        init();
    }

    @SneakyThrows
    private void init() {
        methodMap.put(CargoArea.class, this.getClass().getMethod("setSelectedCargoArea"));
        methodMap.put(FuelTank.class, this.getClass().getMethod("setSelectedFuelTank"));
    }

    public void addListener(BlueprintSelectionListener listener) {
        this.listenerSet.add(listener);
    }

    @SneakyThrows
    public void setSelectedCompartment(Compartment area, BlueprintSelectionModel sm) {
        this.compartment = area;
        this.listenerSet.forEach(listener -> {
            listener.compartmentSelected(compartment);
        });
        methodMap.get(area.getClass()).invoke(sm);
    }

    public void setSelectedCargoArea() {
        this.listenerSet.forEach(listener -> {
            listener.compartmentSelected((CargoArea) compartment); //check integrity all!
        });
    }
    public void setSelectedFuelTank() {
        this.listenerSet.forEach(listener -> {
            listener.compartmentSelected((FuelTank) compartment);
        });
    }
}
