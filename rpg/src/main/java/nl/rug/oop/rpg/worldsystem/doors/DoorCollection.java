package nl.rug.oop.rpg.worldsystem.doors;
import java.util.*;


public class DoorCollection<T> {
    private final NavigableMap<Double, T> collect = new TreeMap<Double, T>();
    private final Random rand;
    private double cumulative = 0;
    public DoorCollection() {
        this(new Random());
    }
    public DoorCollection(Random random) {
        this.rand = random;
    }

    public DoorCollection<T> add(double probability, T doortype) {
        if (probability <= 0) return this;
        cumulative += probability;
        collect.put(cumulative, doortype);
        return this;
    }
    public T extractDoor() {
        double value = rand.nextDouble() * cumulative;
        return collect.higherEntry(value).getValue();
    }
}
