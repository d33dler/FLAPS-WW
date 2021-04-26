package nl.rug.oop.rpg;

import java.util.*;

public abstract class Parameters {

    //inspect possiblity of chaining to abstract class
}

class InitRoom extends Parameters {
    protected String id;
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected int ndors;
    protected List<Door> doors;
    protected boolean company;
    protected ConsumablesDb loot;
    protected NPC npc;

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

    public InitRoom lLoot(ConsumablesDb item) {
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

class PrmtDoor extends Parameters {
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

class InitEntity extends Parameters {
    protected String name;
    protected int health,damage,money;
    protected List<Item> inventory;
    protected Room location;
    public InitEntity name(String name) {
        this.name = name;
        return this;
    }
    public InitEntity hdm(int health, int damage, int money) {
        this.health = health;
        this.damage = damage;
        this.money = money;
        return this;
    }
    public InitEntity inv(List<Item> inventory) {
        this.inventory = inventory;
        return this;
    }
    public InitEntity loc(Room loc) {
        this.location = loc;
        return this;
    }

    public Player protagonist() {
        return new Player(this);
    }
    public Enemies createn() {
        return new Enemies(this);
    }
    public Allies createfr() {
        return new Allies(this);
    }
}