package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.npcsystem.Allies;
import nl.rug.oop.rpg.npcsystem.Enemies;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.util.concurrent.ThreadLocalRandom;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class CloneDoor extends Door implements DoorClass {
    protected boolean used;

    public CloneDoor(DoorBuilder parameters) {
        super(parameters);
        this.dtype = "CopyGate";
        this.used = false;
    }

    public CloneDoor() {
        this.probab = 10;
    }

    @Override
    public void inspect(Player x) {
        super.inspect(x);
        String variant = checkState(x);
        x.getTw().type("It will leave a " + variant + " clone of yourself.\n");
    }

    @Override
    public void interact(Player x) {
        CloneDoor door = (CloneDoor) x.getFocus();
        if (!x.isRabbit() && !door.isUsed()) {
            Room room = x.getLocation();
            if (x.isHostile()) {
                room.setNpc(genHostileClone(x));
            } else {
                room.setNpc(genFriendlyClone(x));
            }
            super.interact(x);
            String variant = checkState(x);
            x.getTw().poeticPause("Notification:\n", 1000);
            x.getTw().type(" CG synthesized a " + variant + " clone of yourself in room: " + room.getId() + "\n");
            door.setUsed(true);
        } else {
            super.interact(x);
        }
    }

    public NPC genFriendlyClone(Player x) {
        return new EntityBuilder() //presence of npc may be randomized
                .name("CG" + x.getName())
                .hdm(x.getHealth(), x.getDamage(), x.getMoney())
                .loc(x.getLocation())
                .inv(x.getInventory())
                .ith(x.getHold())
                .createfr();
    }

    public NPC genHostileClone(Player x) {
        return new EntityBuilder() //presence of npc may be randomized
                .name("CG" + x.getName())
                .hdm(x.getHealth() + 30,
                        x.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), x.getMoney())
                .loc(x.getLocation())
                .inv(x.getInventory())
                .ith(x.getHold())
                .createn();
    }

    public String checkState(Player x) {
        if (x.isHostile()) {
            return ("hostile");
        } else {
            return ("friendly");
        }
    }

    @Override
    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr)
                .createCd();
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
