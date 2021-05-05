package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.itemsystem.Holders;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.security.SecureRandom;
import java.util.*;

public class Room implements Inspectable {
    protected String id;
    protected Attr1room atr1; //string might suffice, check
    protected Attr2room atr2;
    protected int ndors;
    protected List<Door> doors;
    protected boolean company;
    protected Item loot; //variety of loot wep/etc
    protected NPC npc;
    protected Holders storage;

    private static final SecureRandom random = new SecureRandom();

    public Room(RoomBuilder parameters) {
        this.id = parameters.id;
        this.atr1 = parameters.atr1;
        this.atr2 = parameters.atr2;
        this.ndors = parameters.ndors;
        this.doors = parameters.doors;
        this.company = parameters.company;
        this.loot = parameters.loot;
        this.npc = parameters.npc;
        this.storage = parameters.storage;
    }

    public Room() {
    }

    public String getId() {
        return id;
    }

    protected Room randomRoom(List<Room> set) {
        int x = random.nextInt(set.size());
        return set.get(x);
    }

    public List<Door> getDoors() {
        return doors;
    }

    public NPC getNpc() {
        return this.npc;
    }

    @Override
    public void inspect(Player r) {
        Room now = r.getLocation();
        System.out.println("\n You are in a" + now.atr1.getAtt1() + "room" + now.atr2.getAtt2());
        System.out.println(" There are " + now.ndors + " doors here.\n ");
    }

    public Item getLoot() {
        return loot;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public Holders getStorage() {
        return storage;
    }

    public void setLoot(Item loot) {
        this.loot = loot;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }

    public boolean isCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }
}
