package nl.rug.oop.rpg.npcsystem;
import java.util.*;
import java.util.TreeMap;

public class NpcCollection<T> {
    private final NavigableMap<Double, T> collect = new TreeMap<Double, T>();
    private final Random rand;
    private double cumulative = 0;
    public NpcCollection() {
        this(new Random());
    }
    public NpcCollection(Random random) {
        this.rand = random;
    }

    public NpcCollection<T> add(double probability, T doortype) {
        if (probability <= 0) return this;
        cumulative += probability;
        collect.put(cumulative, doortype);
        return this;
    }
    public T extractNpc() {
        double value = rand.nextDouble() * cumulative;
        return collect.higherEntry(value).getValue();
    }
}
