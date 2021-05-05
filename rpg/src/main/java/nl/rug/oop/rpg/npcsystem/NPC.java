package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

public abstract class NPC extends Entity implements Inspectable, Interactable {

    public NPC(EntityBuilder parameters) {
        super(parameters);
    }

    @Override
    public void inspect(Player x) {
        Room now = x.getLocation();
        NPC npc = now.getNpc();
        String name = npc.getName();
        System.out.println("You see a " + name + "");
        if (npc.health <= 0) {
            System.out.println("It's system components are not active\n ");
        }
    }

}

