package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.*;
import nl.rug.oop.rpg.itemsystem.*;
import nl.rug.oop.rpg.npcsystem.Enemies;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static nl.rug.oop.rpg.Randomizers.getRandom;
import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class World {
    protected int links = 100;//edit for requirement
    protected Map<Room, List<Room>> roomConnects;
    private static final Randomizers roomID = new Randomizers(7, ThreadLocalRandom.current());

    public void generateRoom(World map) {
        List<Door> doors = new ArrayList<>();
        String roomId = roomID.generateId();
        Attr1room att1 = randomMaterial(Attr1room.class);
        Attr2room att2 = randomMaterial(Attr2room.class);
        Holders storage = randomMaterial(Holders.class);
        Item loot = generateItem(roomId);
        NPC npc = generateNpc();
        boolean company = npc != null;
        Room room = new RoomBuilder()
                .id(roomId)
                .describe(att1, att2)
                .nrdors(0)
                .lDoors(doors)
                .pComp(company)
                .gNPC(npc)
                .storage(storage)
                .lLoot(loot)
                .create();
        npc.setLocation(room);
        map.roomConnects.put(room, new ArrayList<>());
    }

    public void generateDoor(Room out, Room goin, World map, DoorCollection<Object> doorcoll)
            throws InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {

        Object randoor = doorcoll.extractDoor();
      //  System.out.println("Door class: " + randoor.getClass().getName() );
        Class<?> dtype = randoor.getClass();
        Door door = (Door) dtype.getDeclaredConstructor().newInstance();
        door = door.initConstructor(out, goin);

        out.doors.add(door);
        goin.doors.add(door);
        out.ndors++;
        goin.ndors++;
        map.roomConnects.get(out).add(goin);
        map.roomConnects.get(goin).add(out);
    }


    public DoorCollection<Object> genDoorTypeCollection() {
        CloneDoor cd = new CloneDoor();
        UltraDoor ud = new UltraDoor();
        SecureDoor sd = new SecureDoor();
        Door gdoor = new Door();
        return new DoorCollection<>()
                .add(cd.getProbab(), cd)
                .add(ud.getProbab(), ud)
                .add(sd.getProbab(), sd)
                .add(gdoor.getProbab(), gdoor);
    }

    public NPC generateNpc() {
        SpeciesDb npcdata = randomMaterial(SpeciesDb.class);
        Inventory inventory = new Inventory().generateInv();
        Enemies npc = new EntityBuilder() //presence of npc may be randomized
                .name(npcdata.getSpname())
                .hdm(npcdata.getHealth(),
                        npcdata.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), ThreadLocalRandom.current().nextInt(0, 15))
                .loc(null)
                .inv(inventory)
                .ith(null)//get this man his shield
                .createn();
        return npc;
    }

    public Item generateItem(String in) {
        Item item;
        if (roomID.itemInsert(in)) {
            ConsumablesDb loot = randomMaterial(ConsumablesDb.class);
            item = new ItemBuilder()
                    .name(loot.getConsid())
                    .hh(loot.getHealth())
                    .val(loot.getValue())
                    .createCons();
        } else {
            WeaponsDb loot = randomMaterial(WeaponsDb.class);
            item = new ItemBuilder()
                    .name(loot.getWname())
                    .dmg(loot.getDmg())
                    .val(loot.getValue())
                    .createWep();
        }
        return item;
    }

    public Player generatePlayer(World map) {
        Inventory inv = new Inventory().generateInv(); //verify integrity
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Player player;
        player = new EntityBuilder()
                .name("user")
                .hdm(100, 15, 30)
                .inv(inv)
                .loc(listrm.get(0))
                .fl(false)
                .ith(new ItemBuilder().name("Raygun").dmg(14).val(10).createWep())
                .npcc(listrm.get(0).getNpc())
                .protagonist();
        player.setEnergycells(getRandom(200, 350));
        player.setUsed(listrm.get(0).getDoors().get(0));
        Item itemc = map.generateItem("11a");
        Item itemw = map.generateItem("Xz");
        player.getInventory().getwList().put(itemw.getName(), (Weapons) itemw);
        player.getInventory().getcList().put(itemc.getName(), (Consumables) itemc);
        return player;
    }

    /**
     * @return Map structure, mapping a room to a list of rooms which can be reached through
     * doors which serve as "edges" in this graph-structure based map.
     * Rooms are picked randomly and assigned uniformly (2 - 3) branching doors.
     * No self-loops.
     */

    public World createMap() {
        World map = new World();
        int rooms = 50;
        map.roomConnects = new HashMap<>();
        for (int i = 0; i < rooms; i++) {
            map.generateRoom(map);
        }
        DoorCollection<Object> dcoll = genDoorTypeCollection();

        List<Room> allrooms = new ArrayList<>(map.roomConnects.keySet());
        Room mutate = new Room();
        for (int i = 0; i < links; ) {
            Room rA = mutate.randomRoom(allrooms);
            Room rB = mutate.randomRoom(allrooms);
            if (!rA.id.equals(rB.id) && !map.roomConnects.get(rA).contains(rB)) {
                if (rA.ndors < 4 && rB.ndors < 4) {
                    try {
                        generateDoor(rA, rB, map, dcoll);
                    } catch (InstantiationException | IllegalAccessException |
                            NoSuchMethodException | InvocationTargetException e) {
                        System.out.println("Error generating world.");
                    }
                    i++;
                } else {
                    removeRooms(rA, rB, allrooms);
                }
            }
            if (allrooms.size() == 0) {
                break;
            }
        }
        return map;
    }

    public void removeRooms(Room rA, Room rB, List<Room> allrooms) {
        if (rA.getDoors().size() < 4) {
            allrooms.remove(rB);
        } else {
            allrooms.remove(rA);
        }
    }


}
