package nl.rug.oop.rpg.worldsystem;
import nl.rug.oop.rpg.*;
import nl.rug.oop.rpg.itemsystem.*;
import nl.rug.oop.rpg.npcsystem.*;
import nl.rug.oop.rpg.worldsystem.doors.*;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static nl.rug.oop.rpg.Randomizers.getRandom;
import static nl.rug.oop.rpg.Randomizers.randomMaterial;

/**
 * World class creates a randomly generated Map (Hashmap) using secured Random utility ;
 * and connects the objects of the Door class and its subtypes
 * and the objects of the Room class and its subtypes.
 */
public class World implements Serializable {
    private static final long serialVersionUID = 33L;

    protected Map<Room, List<Room>> roomConnects;
    private transient static final Randomizers roomID = new Randomizers(7, ThreadLocalRandom.current());

    /**
     *
     * @param map collects the newly generated room into the Map hashmap;
     * @param npcCollection is the collection of all NPCs subtypes instances in different numbers
     *                   based on creation probability of the NPC subtype.
     * @throws Exception
     */
    public void generateRoom(World map, NpcCollection<Object> npcCollection) throws Exception {
        ArrayList<Door> doors = new ArrayList<>();
        String roomId = roomID.generateId();
        Attr1room att1 = randomMaterial(Attr1room.class);
        Attr2room att2 = randomMaterial(Attr2room.class);
        Holders storage = randomMaterial(Holders.class);
        Item loot = generateItem(roomId);
        NPC npc = generateNpc(npcCollection);
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

    /**
     *
     * @param out Room node A
     * @param goin Room node B
     * @param map Hashmap  World map
     * @param doorCollection collection of different Door subtypes and the parent Door type class instances;
     * @throws Exception
     */
    public void generateDoor(Room out, Room goin, World map, DoorCollection<Object> doorCollection)
            throws Exception {
        Object randoor = doorCollection.extractDoor();
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

    /**
     *
     * @param configs Using config file to set up probabilities of Door types creation
     * @return
     */
    public DoorCollection<Object> genDoorTypeCollection(Properties configs) {
        CloningDoor cd = new CloningDoor();
        RabitDoor ud = new RabitDoor();
        SecureDoor sd = new SecureDoor();
        Door gdoor = new Door();
        return new DoorCollection<>()
                .add(Integer.parseInt(configs.getProperty("copy%")), cd)
                .add(Integer.parseInt(configs.getProperty("ultra%")), ud)
                .add(Integer.parseInt(configs.getProperty("crypt%")), sd)
                .add(Integer.parseInt(configs.getProperty("door%")), gdoor);
    }

    /**
     *
     * @param npcCollection using the collection to generate randomly Npcs
     * @return NPC subclass instance
     * @throws Exception
     */
    public NPC generateNpc(NpcCollection<Object> npcCollection) throws Exception {
        Object randomNpc = npcCollection.extractNpc();
        Class<?> npcType = randomNpc.getClass();
        NPC npc = (NPC) npcType.getDeclaredConstructor().newInstance();
        npc = npc.initConstructor();
        return npc;
    }

    public NpcCollection<Object> genNpcSpeciesCollection() {
        Enemies enemy = new Enemies();
        Allies ally = new Allies();
        ExchangeBots exBots = new ExchangeBots();
        return new NpcCollection<>().add(enemy.getProbability(), enemy)
                .add(ally.getProbability(), ally)
                .add(exBots.getProbability(), exBots);
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

    /**
     *
     * @return Player class instance
     */
    public Player generatePlayer() {
        Inventory inv = new Inventory().generateInv();
        Player player = new EntityBuilder()
                .name("user")
                .hdm(100, 15, 30)
                .inv(inv)
                .fl(false)
                .ith(new ItemBuilder().name("Raygun").dmg(14).val(10).createWep())
                .createProtagonist();
        player.setEnergycells(getRandom(200, 350));
        return player;
    }

    public Player setupPlayer(Player player, World map) {
        List<Room> listRooms = new ArrayList<>(map.getRoomConnects().keySet());
        player.setLocation(listRooms.get(0));
        player.setNpccontact(listRooms.get(0).getNpc());
        player.setUsed(listRooms.get(0).getDoors().get(0));
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

    public World createMap(Player player) throws Exception {
        Properties configs = player.getConfigs();
        World map = new World();
        DoorCollection<Object> doorCollection = genDoorTypeCollection(configs);
        NpcCollection<Object> npcCollection = genNpcSpeciesCollection();
        int rooms = Integer.parseInt(configs.getProperty("roomNr"));
        int links = rooms * 2 - 20;
        map.roomConnects = new HashMap<>();
        for (int i = 0; i < rooms; i++) {
            map.generateRoom(map, npcCollection);
        }

        List<Room> allrooms = new ArrayList<>(map.roomConnects.keySet());
        Room mutate = new Room();
        for (int i = 0; i < links; ) {
            Room rA = mutate.randomRoom(allrooms);
            Room rB = mutate.randomRoom(allrooms);
            if (!rA.id.equals(rB.id) && !map.roomConnects.get(rA).contains(rB)) {
                if (rA.ndors < 4 && rB.ndors < 4) {
                    try {
                        generateDoor(rA, rB, map, doorCollection);
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

    /**
     *
     * @param rA room A
     * @param rB room B
     * @param allrooms list of all rooms from which we eliminate rooms that already reached
     *                 the capped limit of 4 doors assigned to 1 room.
     */
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
