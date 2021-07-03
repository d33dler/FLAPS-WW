package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.FlapsDatabases;
import nl.rug.oop.flaps.aircraft_editor.view.pass_editor.BlankField;

/**
 * Abstract class defining attributes of any Traveler object (e.g. Pilots, Load-masters, etc)
 * All fields here contain annotation meta-data from BlankField, which defines the field's id (to id specific fields
 * and install constraints for each of them)
 */
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
