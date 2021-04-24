package nl.rug.oop.rpg;

import java.util.*;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    protected int links = 10;

    private Map<Room, List<Room>> roomConnects;
    private static final SecureRandom random = new SecureRandom();
    private static final RandomID roomID = new RandomID(7, ThreadLocalRandom.current());

    protected <T extends Enum<?>> T randomMtrl(Class<T> set) {
        int x = random.nextInt(set.getEnumConstants().length);
        return set.getEnumConstants()[x];
    }

    public void genRoom() {
        List<Door> doors = null; //needs list of doors, and number(possibly)
        Attr1room att1 = randomMtrl(Attr1room.class);
        Attr2room att2 = randomMtrl(Attr2room.class);
        SpeciesDb npc = randomMtrl(SpeciesDb.class); //optional basedamage++=random?; //2.more npcs per room
        ConsumablesDb loot = randomMtrl(ConsumablesDb.class); // needs random loot wep/consum/etc..
        boolean company = npc != null;
        Room room = new Initroom()
                .id(roomID.generateId())
                .describe(att1, att2)
                .lDoors(doors)
                .pComp(company)
                .gNPC(npc)
                .lLoot(loot)
                .create();
        roomConnects.putIfAbsent(room, new ArrayList<>());
    }

    public void genDoor(Room out, Room goin) {
        DoorcolorsDb clr = randomMtrl(DoorcolorsDb.class);
        roomConnects.get(out).add(goin);
        roomConnects.get(goin).add(out);
    }

    World createMap() {
        World map = new World();
        for (int i = 0; i < 20; i++) {
            map.genRoom();
        }
        List<Room> allrooms = new ArrayList<>(roomConnects.keySet());
        Room mutate = new Room(new Initroom());
        for (int i = 0; i < links; ) {
            Room rA = mutate.randomRoom(allrooms);
            Room rB = mutate.randomRoom(allrooms);
            if (!rA.id.equals(rB.id) && rA.doors.size()<3 && rB.doors.size()<3) {
                genDoor(rA,rB);
                i++;
            }
        }

        return new World();
    }

}
