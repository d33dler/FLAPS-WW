package nl.rug.oop.rpg.worldsystem;
import nl.rug.oop.rpg.*;
import nl.rug.oop.rpg.itemsystem.*;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.*;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static nl.rug.oop.rpg.Randomizers.getRandom;
import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class World implements Serializable {
    private static final long serialVersionUID = 33L;

    protected Map<Room, List<Room>> roomConnects;
    private transient static final Randomizers roomID = new Randomizers(7, ThreadLocalRandom.current());

    public void generateRoom(World map) {
        ArrayList<Door> doors = new ArrayList<>();
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
        assert npc != null;
        npc.setLocation(room);
        map.roomConnects.put(room, new ArrayList<>());
    }

    public void generateDoor(Room out, Room goin, World map, DoorCollection<Object> doorcoll)
            throws Exception {
        Object randoor = doorcoll.extractDoor();
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


    public DoorCollection<Object> genDoorTypeCollection(Properties configs) {
        CloningDoor cd = new CloningDoor();
        UltraDoor ud = new UltraDoor();
        SecureDoor sd = new SecureDoor();
        Door gdoor = new Door();
        return new DoorCollection<>()
                .add(Integer.parseInt(configs.getProperty("copy%")), cd)
                .add(Integer.parseInt(configs.getProperty("ultra%")), ud)
                .add(Integer.parseInt(configs.getProperty("crypt%")), sd)
                .add(Integer.parseInt(configs.getProperty("door%")), gdoor);
    }

    public NPC generateNpc() {
        SpeciesDb npcdata = randomMaterial(SpeciesDb.class);
        Inventory inventory = new Inventory().generateInv();
        return new EntityBuilder()
                .name(npcdata.getSpname())
                .hdm(npcdata.getHealth(),
                        npcdata.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), ThreadLocalRandom.current().nextInt(0, 15))
                .loc(null)
                .inv(inventory)
                .ith(null)
                .createn();
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

    public Player generatePlayer() {
        Inventory inv = new Inventory().generateInv();
        Player player = new EntityBuilder()
                .name("user")
                .hdm(100, 15, 30)
                .inv(inv)
                .fl(false)
                .ith(new ItemBuilder().name("Raygun").dmg(14).val(10).createWep())
                .protagonist();
        player.setEnergycells(getRandom(200, 350));
        return player;
    }

    public Player setupPlayer(Player player, World map) {
        List<Room> listrm = new ArrayList<>(map.getRoomConnects().keySet());
        player.setLocation(listrm.get(0));
        player.setNpccontact(listrm.get(0).getNpc());
        player.setUsed(listrm.get(0).getDoors().get(0));
        Item itemc = map.generateItem("11a");
        Item itemw = map.generateItem("Xz");
        player.getInventory().getwList().put(itemw.getName(), (Weapons) itemw);
        player.getInventory().getcList().put(itemc.getName(), (Consumables) itemc);
        return player;
    }

    /**
     * @param player carries the configurations files needed for map parameter settings.
     * @return Map structure, mapping a room to a list of rooms which can be reached through
     * doors which serve as "edges" in this graph-structure based map.
     * Rooms are picked randomly and assigned uniformly (2 - 3) branching doors.
     * No self-loops.
     */

    public World createMap(Player player) {
        Properties configs = player.getConfigs();
        World map = new World();
        int rooms = Integer.parseInt(configs.getProperty("roomNr"));
        int links = rooms * 2 - 20;
        map.roomConnects = new HashMap<>();
        for (int i = 0; i < rooms; i++) {
            map.generateRoom(map);
        }
        DoorCollection<Object> dcoll = genDoorTypeCollection(configs);
        List<Room> allrooms = new ArrayList<>(map.roomConnects.keySet());
        Room mutate = new Room();
        for (int i = 0; i < links; ) {
            Room rA = mutate.randomRoom(allrooms);
            Room rB = mutate.randomRoom(allrooms);
            if (!rA.id.equals(rB.id) && !map.roomConnects.get(rA).contains(rB)) {
                if (rA.ndors < 4 && rB.ndors < 4) {
                    try {
                        generateDoor(rA, rB, map, dcoll);
                    } catch (Exception e) {
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

    public Map<Room, List<Room>> getRoomConnects() {
        return roomConnects;
    }
}
