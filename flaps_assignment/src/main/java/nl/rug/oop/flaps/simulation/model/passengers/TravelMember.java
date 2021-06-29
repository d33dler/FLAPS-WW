package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.FlapsDatabases;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;

@Getter
@Setter
@FlapsDatabases
public abstract class TravelMember {
    @BlankField(id = "name", pattern = "[a-zA-Z]+")
    public String name;
    @BlankField(id = "surname", pattern = "[a-zA-Z]+")
    public String surname;
    @BlankField(id = "nation", pattern = "[a-zA-Z]+")
    public String nationality;
    @BlankField(id = "pass_id")
    public String passportId;
    @BlankField(id = "travel_id")
    public String travellerId;
    @BlankField(id = "age", pattern = "[0-9]+", min = 0, max = 100)
    public String age;

}
