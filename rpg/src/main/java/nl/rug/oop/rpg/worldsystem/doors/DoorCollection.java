package nl.rug.oop.rpg.worldsystem.doors;
import java.util.*;


public class DoorCollection<T> {
    private final NavigableMap<Double, T> collect = new TreeMap<Double, T>();
    private final Random random;
    private double total = 0;

    public DoorCollection() {
        this(new Random());
    }

    public DoorCollection(Random random) {
        this.random = random;
    }

    public DoorCollection<T> add(double probab, T doortype) {
        if (probab <= 0) return this;
        total += probab;
        collect.put(total, doortype);
        return this;
    }
    public T extractDoor() {
        double value = random.nextDouble() * total;
        return collect.higherEntry(value).getValue();
    }
}
