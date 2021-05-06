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
        this.dtype = "CryPT";
        this.open = false;
    }

    public SecureDoor() {
        this.probab = 15;
    }

    @Override
    public void inspect(Player r) {
        super.inspect(r);
        if (!r.getFocus().open) {
            int cost = r.getFocus().getCost();
            int lvl = cost / 20;
            r.getTw().type("Security Level:" + lvl + "\nRequires " + cost + " decryption-device super-cells for computing power\n");

        } else {
            r.getTw().type("You have decrypted this door's lock\n");
        }
    }

    @Override
    public void interact(Player x) {
        if (!x.isRabbit() || !x.getFocus().open) {
            x.getTw().type("You have " + x.getEnergycells() + " cells\n");
            if (x.getEnergycells() < x.getFocus().cost) {
                x.getTw().type("You don't have enough energy cells\n");
            } else {
                x.getTw().type("Charging :  \n" + x.getFocus().cost + " energy cells.");
                x.setEnergycells(x.getEnergycells() - x.getFocus().cost);
                x.getFocus().setCost(0);
                x.getFocus().setOpen(true);
                super.interact(x);
            }
        } else {
            super.interact(x);
        }
    }

    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr)
                .createSd();
    }
}
