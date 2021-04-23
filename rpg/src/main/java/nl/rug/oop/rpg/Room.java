package nl.rug.oop.rpg;
import java.util.*;

public class Room implements Inspectable{
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected List<Door> doors;
    protected boolean company;
    protected ConsumablesDb loot; //variety of loot wep/etc
    protected NPC npc;
    public void inspect() {

    }

    public Room (Prmtroom parameters){
        this.atr1 = parameters.atr1;
        this.atr2 = parameters.atr2;
        this.doors = parameters.doors;
        this.company = parameters.company;
        this.loot = parameters.loot;
    }
}
