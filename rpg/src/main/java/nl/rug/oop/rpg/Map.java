package nl.rug.oop.rpg;
import java.util.*;
import java.security.SecureRandom;
public class Map {


    private static final SecureRandom random = new SecureRandom();
    private static <T extends Enum<?>> T randomEnum(Class<T> set){
        int x = random.nextInt(set.getEnumConstants().length);
        return set.getEnumConstants()[x];
    }
    public void genDoor() {

    }
    public void genRoom() {
        List<Door> doors = null; //needs random
        Attr1room att1 = randomEnum(Attr1room.class);
        Attr2room att2 = randomEnum(Attr2room.class);
        SpeciesDb npc = randomEnum(SpeciesDb.class); //optional basedamage++=random?;
        ConsumablesDb loot = randomEnum(ConsumablesDb.class); // needs random loot wep/consum/etc..
         boolean company = npc != null;
        Room roomdata = new Prmtroom()
                .describe(att1,att2)
                .lDoors(doors)
                .pComp(company)
                .gNPC(npc)
                .lLoot(loot)
                .create();
    }


}
