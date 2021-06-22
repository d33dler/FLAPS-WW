package nl.rug.oop.flaps.simulation.model.aircraft.areas;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Components extends Compartment {

    public abstract float getTotalHealth();

}
