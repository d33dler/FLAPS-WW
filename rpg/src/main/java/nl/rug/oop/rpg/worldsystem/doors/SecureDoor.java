package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.io.Serializable;

import static nl.rug.oop.rpg.Randomizers.getRandom;
import static nl.rug.oop.rpg.Randomizers.randomMaterial;

/**
 * SecureDoor is a Door subclass which overrides the behaviour of the parent function.
 */
public class SecureDoor extends Door implements Serializable {
    private static final long serialVersionUID = 102;

    /**
     * @param parameters constructor using the default builder constructor of the parent class
     *                   and adding a random integer cost field value and type name for identification
     *                   purposes.
     */
    public SecureDoor(DoorBuilder parameters) {
        super(parameters);
        this.cost = getRandom(5, 100);
        this.doorType = "CryPT";
    }

    /**
     * Secondary constructor for probability settings & collection creation.
     */
    public SecureDoor() {
        this.probab = 15;
    }

    /**
     * uses the default parent method inspect() and overrides it by appending
     * addition information delivered to the user, based on the boolean field
     * value 'open' of the door.
     */
    @Override
    public void inspect(Player player) {
        super.inspect(player);
        if (!player.getDoorFocus().open) {
            int cost = player.getDoorFocus().getCost();
            int lvl = cost / 20;
            player.getTw().type("Security Level:" + lvl + "\nRequires " + cost + " decryption-device super-cells for computing power\n");

        } else {
            player.getTw().type("You have decrypted this door's lock\n");
        }
    }

    /**
     * interact() override: SecureDoor requires unlocking before entering by using
     * an integer value 'energy cells' from which cost is subtracted based on the cost
     * of the SecureDoor 'unlocking' which sets the field value 'open' to true if
     * the player has enough energy cells. Open secureDoors don't require a second unlocking.
     * And the player can't trespass a SecureDoor while traversing the map in a RaBIT drift-dive state.
     */
    @Override
    public void interact(Player player) {
        if (!player.isRabbit() && !player.getDoorFocus().open) {
            player.getTw().type("You have " + player.getEnergycells() + " cells\n");
            if (player.getEnergycells() < player.getDoorFocus().cost) {
                player.getTw().type("You don't have enough energy cells\n");
            } else {
                player.getTw().type("Charging :  \n" + player.getDoorFocus().cost + " energy cells.");
                player.setEnergycells(player.getEnergycells() - player.getDoorFocus().cost);
                player.getDoorFocus().setCost(0);
                player.getDoorFocus().setOpen(true);
                super.interact(player);
            }
        } else if (!player.isRabbit()) {
            super.interact(player);
        } else {
            notifyHalt(player);
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
                .createSd();
    }
}
