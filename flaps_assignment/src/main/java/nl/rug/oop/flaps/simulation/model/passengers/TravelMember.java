package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.FlapsDatabases;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;

@Getter
@Setter
@FlapsDatabases
public abstract class TravelMember {
    @BlankField(id = "name")
    public String name;
    @BlankField(id = "surname")
    public String surname;
    @BlankField(id = "nation")
    public String nationality;
    @BlankField(id = "pass_id")
    public String passportId;
    @BlankField(id = "travel_id")
    public String travellerId;
    @BlankField(id = "age")
    public String age;

}
