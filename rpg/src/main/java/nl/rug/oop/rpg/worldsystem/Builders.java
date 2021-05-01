package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.itemsystem.Holders;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.npcsystem.NPC;

import java.util.*;

public abstract class Builders {

    //inspect possiblity of chaining to abstract class
}

class InitRoom extends Builders {
    protected String id;
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected int ndors;
    protected List<Door> doors;
    protected boolean company;
    protected Item loot;
    protected NPC npc;
    protected Holders storage;

    public InitRoom id(String id) {
        this.id = id;
        return this;
    }

    public InitRoom describe(Attr1room atr1, Attr2room atr2) {
        this.atr1 = atr1;
        this.atr2 = atr2;
        return this;
    }

    public InitRoom nrdors(int cdors) {
        this.ndors = cdors;
        return this;
    }

    public InitRoom lDoors(List<Door> doors) {
        this.doors = doors;
        return this;
    }

    public InitRoom pComp(boolean cmp) { //present company
        this.company = cmp;
        return this;
    }

    public InitRoom gNPC(NPC npc) {
        this.npc = npc;
        return this;
    }
    public InitRoom storage(Holders storage){
        this.storage = storage;
        return this;
    }

    public InitRoom lLoot(Item item) {
        this.loot = item;
        return this;
    }
    public Room create() {
        return new Room(this);
    }

    public String getId() {
        return id;
    }
}

class PrmtDoor extends Builders {
    protected DoorcolorsDb color;
    protected Room exit, enter;

    protected boolean open, locked;
    /* key- requires hashmap sharing key with door*/

    public PrmtDoor exit(Room exit) {
        this.exit = exit;
        return this;
    }

    public PrmtDoor enter(Room enter) {
        this.enter = enter;
        return this;
    }

    public PrmtDoor clr(DoorcolorsDb color) {
        this.color = color;
        return this;
    }


    public Door create() {
        return new Door(this);
    }
    // public Prmtdoor(boolean open) {
    //     this.open = open;
    // }

}

