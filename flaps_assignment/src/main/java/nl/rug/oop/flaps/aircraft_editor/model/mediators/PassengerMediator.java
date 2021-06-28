package nl.rug.oop.flaps.aircraft_editor.model.mediators;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.aircraft_editor.controller.configcore.ControlSolicitor;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;
import nl.rug.oop.flaps.simulation.model.loaders.FileUtils;
import nl.rug.oop.flaps.simulation.model.passengers.Passenger;
import nl.rug.oop.flaps.simulation.model.passengers.PassengerType;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Setter
@Getter
public class PassengerMediator implements ControlSolicitor {

    private Passenger selectedPass;
    private PassengerType selectedType;
    private HashMap<String, Passenger> passHashMap = new HashMap<>();
    private List<JTextField> blankSet = new ArrayList<>();
    private HashMap<BlankField, Field> fieldList = new HashMap<>();

    public PassengerMediator() {
        setUpFieldAnnotations();
    }

    public void updateHashedPassenger(Passenger p) {
        passHashMap.put(p.getTravellerId(), p);
    }

    public boolean checkFields() {
        blankSet.forEach(field -> {
            System.out.println(field.getName());
            if (field.getText() != null) {
                System.out.println(field.getText());
            }
        });
        return blankSet.stream().anyMatch(field -> field.getText() == null);

    }

    private void setUpFieldAnnotations() {
        FileUtils.getAllFieldsFiltered(Passenger.class).forEach(field -> {
            BlankField bf = field.getAnnotation(BlankField.class);
            if (bf != null) {
                fieldList.put(bf, field);
            }
        });
    }

    @Override
    public void performPriorUpdates() {
        //TODO
    }
}
