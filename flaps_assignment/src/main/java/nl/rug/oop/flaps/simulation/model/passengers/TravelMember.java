package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TravelMember {
    public String name,
            surname,
            nationality,
            passportId, travellerId;
    public int age;

}
