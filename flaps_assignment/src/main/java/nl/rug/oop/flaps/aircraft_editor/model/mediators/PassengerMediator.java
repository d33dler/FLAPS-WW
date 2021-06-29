package nl.rug.oop.flaps.aircraft_editor.model.mediators;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import java.util.*;

@Setter
@Getter
public class PassengerMediator implements ControlSolicitor {

    private Passenger selectedPass;
    private PassengerType selectedType;
    private HashMap<String, Passenger> passHashMap = new HashMap<>();
    private List<JTextField> blankSet = new ArrayList<>();
    private LinkedHashSet<JTextField> invalidFields = new LinkedHashSet<>();
    private Set<Passenger> passengerSet;

    public PassengerMediator() {
    }

    public void updateHashedPassenger(Passenger p, boolean b) {
        if (b) passHashMap.put(p.getDatabase_id(), p);
        else passHashMap.remove(p.getDatabase_id());
    }

    public boolean checkFields() {
        return blankSet.stream().anyMatch(field -> field.getText().isBlank());
    }

    @Override
    public void performPriorUpdates() {
        //TODO
    }

    @Override
    public Set<?> getDataSet() {
        return passengerSet;
    }
}
