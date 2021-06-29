package nl.rug.oop.flaps.aircraft_editor.model.mediators;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.simulation.model.aircraft.Aircraft;
import nl.rug.oop.flaps.simulation.model.aircraft.areas.CargoArea;
import nl.rug.oop.flaps.simulation.model.cargo.CargoFreight;
import nl.rug.oop.flaps.simulation.model.cargo.CargoType;

import javax.swing.*;
import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
public class CargoMediator implements ControlSolicitor {

    private JTextField quantityField;
    private int quantity;
    private CargoType selectedType;
    private CargoFreight selectedFreight;
    private HashMap<String, CargoType> cargoHashMap = new HashMap<>();
    private HashMap<String, CargoFreight> freightHashMap = new HashMap<>();

    public CargoMediator() {
    }

    public void performPriorUpdates() {
        if (!quantityField.getText().isBlank()) {
            quantity = Integer.parseInt(quantityField.getText());
        }
    }

    /**
     * Store cargo type names and cargo freight IDs for fast retrieval during selections;
     * Storing is dynamical - based on current warehouse types and cargo area's freights;
     */
    public void populateDatabaseHashmaps(Aircraft aircraft, Set<CargoType> typeSet, CargoArea area) {
        typeSet.forEach(cargoType -> cargoHashMap.put(cargoType.getName(), cargoType));
        aircraft.getCargoAreaContents(area).forEach(cargoFreight
                -> freightHashMap.put(cargoFreight.getId(), cargoFreight));
    }

    public void updateHashedFreight(CargoFreight carriage, int amount) {
        if (amount > 0) freightHashMap.put(carriage.getId(), carriage);
        else freightHashMap.remove(carriage.getId());
    }
}
