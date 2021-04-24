package nl.rug.oop.rpg;
import java.util.*;

public class RandomID {
    public String generateId() {
        for (int idx = 0; idx < strand.length; ++idx)
            strand[idx] = symbols[random.nextInt(symbols.length)];
        return new String(strand);
    }
    private final Random random;
    private final char[] symbols;
    private final char[] strand; //random string to be passed
    public static final String upcase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lowcase = upcase.toLowerCase(Locale.ROOT);
    public static final String digits = "1234567890";
    public static final String allchar = upcase + lowcase + digits;
    public RandomID(int length, Random random, String symbols) {
        if (length < 1)
            throw new IllegalArgumentException();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.strand = new char[length];
    }

    /**
     * Generate a random id for the room
     */
    public RandomID(int length, Random random) {
        this(length, random, allchar);
    }


}
