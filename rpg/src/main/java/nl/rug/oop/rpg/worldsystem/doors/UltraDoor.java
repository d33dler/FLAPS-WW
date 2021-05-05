package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class UltraDoor extends Door implements DoorClass {

    public UltraDoor(DoorBuilder parameters) {
        super(parameters);
        this.dtype = "RàBIT";
    }

    public UltraDoor() {
        this.probab = 10;
    }

    @Override
    public void inspect(Player r) {
        super.inspect(r);
        int x = r.getTravelBuff();
        r.getTw().type("It will bypass " + x + " room(s)\n");
    }

    @Override
    public void interact(Player x) {
        Door y = x.getFocus();
        if (!x.isRabbit()) {
            super.interact(x);
            x.setRabbit(true);
            for (int i = 0; i < x.getTravelBuff(); i++) {
                x.setIntin(checkRooms(x));
                x.getTw().poeticPause("Initiating jump\n", 1000);
                x.getTw().type(". . . . .\nBypassing: " + x.getUsed().dtype + "\n");
                super.interact(x);
            }
            x.setRabbit(false);
        } else {
            x.getFocus().notifyHalt(x);
        }
    }

    public int checkRooms(Player x) {
        Room rnow = x.getLocation();
        int i;
        for (i = 0; i < rnow.getDoors().size(); i++) {
            Door dnow = rnow.getDoors().get(i);
            if (!dnow.equals(x.getUsed()) && dnow.dtype.equals("Generic")) {
                break;
            }
        }
        return i;
    }

    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr)
                .createUd();
    }
}
