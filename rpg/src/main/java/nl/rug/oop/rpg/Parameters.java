package nl.rug.oop.rpg;

import java.util.*;

public abstract class Parameters {

    //inspect possiblity of chaining to abstract class
}

class Initroom extends Parameters {
    protected String id;
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected List<Door> doors;
    protected boolean company;
    protected ConsumablesDb loot;
    protected SpeciesDb npc;

    public Initroom id(String id) {
        this.id = id;
        return this;
    }

    public Initroom describe(Attr1room atr1, Attr2room atr2) {
        this.atr1 = atr1;
        this.atr2 = atr2;
        return this;
    }

    public Initroom lDoors(List<Door> doors) {
        this.doors = doors;
        return this;
    }

    public Initroom pComp(boolean cmp) { //present company
        this.company = cmp;
        return this;
    }

    public Initroom gNPC(SpeciesDb npc) {
        this.npc = npc;
        return this;
    }

    public Initroom lLoot(ConsumablesDb item) {
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

class Prmtdoor extends Parameters {
    protected DoorcolorsDb color;
    protected Room exit, enter;
    protected boolean open, locked;
    /* key- requires hashmap sharing key with door*/

    public Prmtdoor exit(Room exit) {
        this.exit = exit;
        return this;
    }

    public Prmtdoor enter(Room enter) {
        this.enter = enter;
        return this;
    }

    public Prmtdoor clr(DoorcolorsDb color) {
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