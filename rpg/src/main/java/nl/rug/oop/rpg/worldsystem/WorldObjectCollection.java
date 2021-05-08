package nl.rug.oop.rpg.worldsystem;
import java.util.*;
import java.util.TreeMap;

/**
 *
 * @param <T> is either Door or one of its subclasses. Or one of the NPC subclasses.
 *           The collection class and its methods are used to yield a NavigableMap
 *           which stores addresses that simulate probability of fetching a certain
 *           object type. It is used to deploy Door or NPC of different types at
 *           different probabilities.
 */
public class WorldObjectCollection<T> {
    private final NavigableMap<Double, T> collect = new TreeMap<Double, T>();
    private final Random rand;
    private double cumulative = 0;
    public WorldObjectCollection() {
        this(new Random());
    }
    public WorldObjectCollection(Random random) {
        this.rand = random;
    }

    public WorldObjectCollection<T> add(double probability, T doortype) {
        if (probability <= 0) return this;
        cumulative += probability;
        collect.put(cumulative, doortype);
        return this;
    }
    public T extractObject() {
        double value = rand.nextDouble() * cumulative;
        return collect.higherEntry(value).getValue();
    }
}
