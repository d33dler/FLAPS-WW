package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.worldsystem.Room;

public abstract class NPC extends Entity implements Inspectable {

    public NPC(EntityBuilder parameters) {
        super(parameters);
    }

    @Override
    public void inspect(Room x) {
        NPC npc = x.getNpc();
        String name = npc.getName();
        System.out.println("You see a " + name + "");
        if (npc.health <= 0) {
            System.out.println("It's system components are not active\n ");
        }
    }


}

