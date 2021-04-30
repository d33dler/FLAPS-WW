package nl.rug.oop.rpg;

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
class InitItem extends Builders {
    protected String name;
    protected int dmg,health,value;

    public InitItem name(String name) {
        this.name = name;
        return this;
    }
    public InitItem dmg(int dmg) {
        this.dmg = dmg;
        return this;
    }
    public InitItem hh(int health){
        this.health = health;
        return this;
    }
    public InitItem val(int value) {
        this.value = value;
        return this;
    }
    public Weapons createWep() {
        return new Weapons(this);
    }
    public Consumables createCons() {
        return new Consumables(this);
    }
    public Nothing createNull() {
        return new Nothing(this);
    }
}
class PlayerInterface extends Builders {
    protected HashMap<String, Weapons> wList;
    protected HashMap<String, Consumables> cList;

    public PlayerInterface weaponInv(HashMap<String, Weapons> wList) {
        this.wList = wList;
        return this;
    }
    public PlayerInterface consumInv(HashMap<String, Consumables> cList) {
        this.cList = cList;
        return this;
    }

    public Inventory createInv() {
        return new Inventory(this);
    }
}

class InitEntity extends Builders {
    protected String name;
    protected int health,damage,money;
    protected Inventory inventory;
    protected Room location;
    protected Item hold;
    protected boolean flee;
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
    public InitEntity inv(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }
    public InitEntity loc(Room loc) {
        this.location = loc;
        return this;
    }
    public InitEntity fl(Boolean flee){
        this.flee = flee;
        return this;
    }
    public InitEntity ith(Item hold) {
        this.hold= hold;
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