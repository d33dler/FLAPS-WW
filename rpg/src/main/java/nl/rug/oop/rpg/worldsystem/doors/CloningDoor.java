package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

/**
 * CloningDoor is a Door subtype.
 * It overrides the parent class' Door behaviour and implements its own constructor.
 */

public class CloningDoor extends Door implements DoorTypology, Serializable {
    private static final long serialVersionUID = 101L;

    /**
     * @param parameters Builder pattern Class for doors.
     */
    public CloningDoor(DoorBuilder parameters) {
        super(parameters);
        this.doorType = "CopyGate";
    }

    /**
     * @param out  Room node A
     * @param goin Room node B
     * @return a rabit door that acts as an edge in the graph-structure based Map.
     */

    @Override
    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr.getCname())
                .vis(false)
                .open(false)
                .createCd();
    }

    public CloningDoor() {
        this.probab = 10;
    }

    /**
     * @param player Player object holds the Door field value doorFocus -
     *               which identifies the door that the player is currently viewing/interacting with.
     *               inspect() Override, gives additional information regarding the door subtype.
     */
    @Override
    public void inspect(Player player) {
        super.inspect(player);
        String variant = checkState(player);
        if (!player.getDoorFocus().isVisited()) {
            player.gettW().type("It will leave a " + variant + " clone of yourself here.\n");
        } else {
            player.gettW().type("You used this CG portal to create a " + variant + " clone.\n");
        }
    }

    /**
     * @param player uses the interact() method behaviour of the parent class Door,
     *               and also adds side-effects: generating an NPC object that contains
     *               identical field values as some of the player object - representing a
     *               clone that is inspectable and interactable.
     */
    @Override
    public void interact(Player player) {
        CloningDoor door = (CloningDoor) player.getDoorFocus();
        if (!player.isRabbit() && !door.isVisited()) {
            Room room = player.getLocation();
            if (player.isHostile()) {
                room.setNpc(genHostileClone(player));
            } else {
                room.setNpc(genFriendlyClone(player));
            }
            super.interact(player);
            String variant = checkState(player);
            player.gettW().poeticPause("Notification:\n", 1000);
            player.gettW().type(" CG synthesized a " + variant + " clone of yourself in room: " + room.getId() + "\n");
            door.setVisited(true);
        } else {
            super.interact(player);
        }
    }

    /**
     * @param player delivers field values for the creation of an Ally NPC parent class subtype
     * @return Ally (child of NPC class) instance .
     */
    public NPC genFriendlyClone(Player player) {
        return new EntityBuilder() //presence of npc may be randomized
                .name("CG" + player.getName())
                .hdm(player.getHealth(), player.getDamage(), player.getMoney())
                .loc(player.getLocation())
                .inv(player.getInventory())
                .ith(player.getHold())
                .createFriend();
    }

    /**
     * @param player delivers field values for the creation of an Enemy NPC parent class subtype
     * @return Enemy (child of NPC class) instance .
     */
    public NPC genHostileClone(Player player) {
        return new EntityBuilder() //presence of npc may be randomized
                .name("CG" + player.getName())
                .hdm(player.getHealth() + 30,
                        player.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), player.getMoney())
                .loc(player.getLocation())
                .inv(player.getInventory())
                .ith(player.getHold())
                .createEnemy();
    }

    /**
     * @param player is checked for its boolean field value for hostility. If
     *               player enters combat - it is set to hostile, thus generating
     *               Enemy NPC instances when using CloningDoors.
     * @return boolean value;
     */
    public String checkState(Player player) {
        if (player.isHostile()) {
            return ("hostile");
        } else {
            return ("friendly");
        }
    }


}
