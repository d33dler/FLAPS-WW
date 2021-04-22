package nl.rug.oop.rpg;

import java.security.SecureRandom;

public class Randomizer {
    private static final SecureRandom random = new SecureRandom();
    public static <T extends Enum<?>> T randomEnum(Class<T> set){
        int x = random.nextInt(set.getEnumConstants().length);
        return set.getEnumConstants()[x];
    }
}
