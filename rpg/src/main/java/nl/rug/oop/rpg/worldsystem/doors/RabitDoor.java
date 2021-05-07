package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

/**
 * RabitDoor is a Door subtype.
 * It overrides the parent class' Door behaviour and implements its own constructor.
 */

public class RabitDoor extends Door implements DoorTypology {
    private static final long serialVersionUID = 100L;

    /**
     *
     * @param parameters Builder pattern Class for doors.
     */
    public RabitDoor(DoorBuilder parameters) {
        super(parameters);
        this.doorType = "RàBIT";
    }

    public RabitDoor() {
        this.probab = 10;
    }

    /**
     *
     * @param out Room node A
     * @param goin Room node B
     * @return a rabit door that acts as an edge in the graph-structure based Map.
     */
    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr.getCname())
                .vis(false)
                .open(false)
                .createUd();
    }

    /**
     *
     * @param player Player object holds the Door field value doorFocus -
     *              which identifies the door that the player is currently viewing/interacting with.
     *               inspect() Override, gives additional information regarding the door subtype.
     */
    @Override
    public void inspect(Player player) {
        super.inspect(player);
        int trBuff = player.getTravelBuff();
        if (!player.getDoorFocus().visited) {
            player.getTw().type("It will bypass " + trBuff + " room(s)\n");
        } else {
            player.getTw().type("You already used the services of this RàBIT migration portal.");
        }
    }

    /**
     *
     * @param player Player object holds the Door field value doorFocus.
     *               interact method Override uses the default behaviour of parent class Door.
     *               It adds additional side-effects. Here: a series (using a loop) of jumps through a number
     *               of other doors - by automatically choosing doors that are compatible
     *               for the travel. The process is halted if no available doors are found and
     *               the user is informed.
     */
    @Override
    public void interact(Player player) {
        Door door = player.getDoorFocus();
        if (!door.visited) {
            if (!player.isRabbit()) {
                super.interact(player);
                player.cappingTravelBuff(player);
                player.setRabbit(true);
                Dialogue.rabitIntro(player);
                for (int i = 0; i < player.getTravelBuff(); i++) {
                    player.setIntin(checkRooms(player));
                    if (!confirmAvailability(player)) {
                        player.setRabbit(false);
                        notifyHalt(player);
                        return;
                    }
                    Door drift = player.getLocation().getDoors().get(player.getIntin());
                    player.setDoorFocus(drift);
                    Dialogue.rabitJumpNotice(player);
                    super.interact(player);
                    Dialogue.rabitJumpConfirm(player);
                }
                player.setRabbit(false);
                Dialogue.rabitService(player);
            } else {
                super.interact(player);
            }
        } else {
            super.interact(player);
        }
    }

    /**
     *
     * @param player is used to get the current location where the available doors are checked.
     * @return is a door number if a compatible door is found. Else return = -1;
     */
    public int checkRooms(Player player) {
        Room roomNow = player.getLocation();
        int i;
        boolean found = false;
        for (i = 0; i < roomNow.getDoors().size(); i++) {
            Door doorNow = roomNow.getDoors().get(i);
            if (!doorNow.isVisited() && doorNow.open) {
                found = true;
                break;
            }
        }
        if (found) {
            return i;
        } else {
            return -1;
        }
    }

    public boolean confirmAvailability(Player player) {
        if (player.getIntin() < 0) {
            player.setIntin(1);
            return false;
        } else {
            return true;
        }
    }


}
