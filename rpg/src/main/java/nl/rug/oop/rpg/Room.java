package nl.rug.oop.rpg;
import java.security.SecureRandom;
import java.util.List;

public class Room {
    protected String color,atr;
    protected List<Door> doors;
    protected Item item;
    protected boolean obj, company;


    private static final SecureRandom random = new SecureRandom();

    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}