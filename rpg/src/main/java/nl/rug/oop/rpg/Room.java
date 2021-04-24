package nl.rug.oop.rpg;
import java.security.SecureRandom;
import java.util.*;

public class Room implements Inspectable{
    protected String id;
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected List<Door> doors;
    protected boolean company;
    protected ConsumablesDb loot; //variety of loot wep/etc
    protected SpeciesDb npc;
    public void inspect() {

    }
    private static final SecureRandom random = new SecureRandom();
    public Room (Initroom parameters){
        this.id = parameters.id;
        this.atr1 = parameters.atr1;
        this.atr2 = parameters.atr2;
        this.doors = parameters.doors;
        this.company = parameters.company;
        this.loot = parameters.loot;
        this.npc = parameters.npc;
    }
    protected Room randomRoom(List<Room> set) {
        int x = random.nextInt(set.size());
        return set.get(x);
    }

}
