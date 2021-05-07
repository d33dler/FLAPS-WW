package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class RabitDoor extends Door implements DoorTypology {
    private static final long serialVersionUID = 100L;

    public RabitDoor(DoorBuilder parameters) {
        super(parameters);
        this.doorType = "RàBIT";
    }

    public RabitDoor() {
        this.probab = 10;
    }

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
}
