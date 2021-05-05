package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.itemsystem.Holders;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.List;

public class RoomBuilder extends Room{

    public RoomBuilder id(String id) {
        this.id = id;
        return this;
    }

    public RoomBuilder describe(Attr1room atr1, Attr2room atr2) {
        this.atr1 = atr1;
        this.atr2 = atr2;
        return this;
    }

    public RoomBuilder nrdors(int cdors) {
        this.ndors = cdors;
        return this;
    }

    public RoomBuilder lDoors(List<Door> doors) {
        this.doors = doors;
        return this;
    }

    public RoomBuilder pComp(boolean cmp) { //present company
        this.company = cmp;
        return this;
    }

    public RoomBuilder gNPC(NPC npc) {
        this.npc = npc;
        return this;
    }

    public RoomBuilder storage(Holders storage) {
        this.storage = storage;
        return this;
    }

    public RoomBuilder lLoot(Item item) {
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
