package nl.rug.oop.flaps.aircraft_editor.controller;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizers {
    /**
     * Generate a random alphanumeric id for the room. Used for both
     * identification of rooms in the world construction methods and
     * the scattering probability of item subtypes per room using RegEx patterns.
     */
    private static final SecureRandom rand = new SecureRandom();
    private final char[] strand;
    private final Random random;
    private final char[] chars;
    public static final String upcase = "ABGWXYZ";
    public static final String lowcase = upcase.toLowerCase(Locale.ROOT);
    public static final String digits = "1234567890";
    public static final String allchar = upcase + lowcase + digits;

    public Randomizers(int length, Random random, String symbols) {
        if (length < 1)
            throw new IllegalArgumentException();
        if (symbols.length() < 2)
            throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.chars = symbols.toCharArray();
        this.strand = new char[length];
    }
    public String generateId() {
        for (int idx = 0; idx < strand.length; ++idx)
            strand[idx] = chars[random.nextInt(chars.length)];
        return new String(strand);
    }
    public static <T extends Enum<?>> T randomMaterial(Class<T> set) {
        int x = rand.nextInt(set.getEnumConstants().length);
        return set.getEnumConstants()[x];
    }
    public Randomizers(int length, Random random) {
        this(length, random, allchar);
    }

    /**
     *
     * @param in roomId string influences the Item type which is created in the room.
     * @return true/false depending on the roomId pattern matching of the regEx pattern.
     */
    public boolean itemInsert(String in) {
        return in.matches("^[0-9|a-z].*$");
    }
    public static int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

