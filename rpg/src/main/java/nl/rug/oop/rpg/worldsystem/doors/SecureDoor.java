package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.io.Serializable;

import static nl.rug.oop.rpg.Randomizers.getRandom;
import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class SecureDoor extends Door implements Serializable {
    private static final long serialVersionUID = 102;

    public SecureDoor(DoorBuilder parameters) {
        super(parameters);
        this.cost = getRandom(5, 100);
        this.doorType = "CryPT";
    }

    public SecureDoor() {
        this.probab = 15;
    }

    @Override
    public void inspect(Player r) {
        super.inspect(r);
        if (!r.getDoorFocus().open) {
            int cost = r.getDoorFocus().getCost();
            int lvl = cost / 20;
            r.getTw().type("Security Level:" + lvl + "\nRequires " + cost + " decryption-device super-cells for computing power\n");

        } else {
            r.getTw().type("You have decrypted this door's lock\n");
        }
    }

    @Override
    public void interact(Player x) {
        if (!x.isRabbit() && !x.getDoorFocus().open) {
            x.getTw().type("You have " + x.getEnergycells() + " cells\n");
            if (x.getEnergycells() < x.getDoorFocus().cost) {
                x.getTw().type("You don't have enough energy cells\n");
            } else {
                x.getTw().type("Charging :  \n" + x.getDoorFocus().cost + " energy cells.");
                x.setEnergycells(x.getEnergycells() - x.getDoorFocus().cost);
                x.getDoorFocus().setCost(0);
                x.getDoorFocus().setOpen(true);
                super.interact(x);
            }
        } else if (!x.isRabbit()) {
            super.interact(x);
        } else {
            notifyHalt(x);
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
