package nl.rug.oop.rpg;

import java.util.*;
import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    protected int links = 34;//edit for requirement

    protected Map<Room, List<Room>> roomConnects;
    private static final SecureRandom random = new SecureRandom();
    private static final RandomID roomID = new RandomID(7, ThreadLocalRandom.current());

    protected <T extends Enum<?>> T randomMtrl(Class<T> set) {
        int x = random.nextInt(set.getEnumConstants().length);
        return set.getEnumConstants()[x];
    }

    public void genRoom(World map) {
        List<Door> doors = new ArrayList<>();
        Attr1room att1 = randomMtrl(Attr1room.class);
        Attr2room att2 = randomMtrl(Attr2room.class);
        SpeciesDb npcdata = randomMtrl(SpeciesDb.class);
        Enemies npc = new InitEntity() //presence of npc may be randomized
                .name(npcdata.getSpname())
                .hdm(npcdata.getHealth(),
                        npcdata.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), ThreadLocalRandom.current().nextInt(0, 15))
                .loc(null)
                .inv(null) //get this man his shield
                .createn();
        ConsumablesDb loot = randomMtrl(ConsumablesDb.class); // needs random loot wep/consum/etc..
        boolean company = npc != null;
        Room room = new InitRoom()
                .id(roomID.generateId())
                .describe(att1, att2)
                .nrdors(0)
                .lDoors(doors)
                .pComp(company)
                .gNPC(npc)
                .lLoot(loot)
                .create();
        npc.location = room;
        map.roomConnects.put(room, new ArrayList<>());
    }

    public void genDoor(Room out, Room goin, World map) {
        DoorcolorsDb clr = randomMtrl(DoorcolorsDb.class);
        Door door = new PrmtDoor()
                .exit(out)
                .enter(goin)
                .clr(clr)
                .create();
        out.doors.add(door);
        goin.doors.add(door);
        out.cdors++;
        goin.cdors++;
        map.roomConnects.get(out).add(goin);
        map.roomConnects.get(goin).add(out);
    }

    public void removeRooms(Room rA, Room rB, List<Room> allrooms) {
        if (rA.doors.size() < 3) {
            allrooms.remove(rA);
        } else {
            allrooms.remove(rB);
        }
    }

    World createMap() {
        World map = new World();
        map.roomConnects = new HashMap<>();
        for (int i = 0; i < 35; i++) {
            map.genRoom(map);
        }
        List<Room> allrooms = new ArrayList<>(map.roomConnects.keySet());
        Room mutate = new Room(new InitRoom());
        for (int i = 0; i < links; ) {
            Room rA = mutate.randomRoom(allrooms);
            Room rB = mutate.randomRoom(allrooms);
            if (!rA.id.equals(rB.id) && !map.roomConnects.get(rA).contains(rB)) {
                if (rA.cdors < 3 && rB.cdors < 3) {
                    genDoor(rA, rB, map);
                    i++;
                } else {
                    removeRooms(rA, rB, allrooms);
                }
            }
        }
        return map;
    }
}
