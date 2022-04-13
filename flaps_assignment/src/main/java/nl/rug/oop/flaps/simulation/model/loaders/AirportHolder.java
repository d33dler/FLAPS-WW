package nl.rug.oop.flaps.simulation.model.loaders;

import com.fasterxml.jackson.annotation.JsonMerge;
import lombok.Getter;
import lombok.Setter;
import nl.rug.oop.flaps.simulation.model.airport.Airport;

@Getter
@Setter
public class AirportHolder {
    @JsonMerge
    private Airport airport;
}
