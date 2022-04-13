package nl.rug.oop.flaps.simulation.model.passengers;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.rug.oop.flaps.FlapsDatabases;


@Setter
@Getter
@FlapsDatabases
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PassengerType implements Comparable<PassengerType>{
    private String name,id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PassengerType pType = (PassengerType) o;
        return getId().equals(pType.getId());
    }
    @Override
    public int compareTo(PassengerType o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public String toString() {
        return name;
    }
}
