package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.io.Serializable;

public abstract class NPC extends Entity implements Inspectable, Interactable, Attackable, Serializable {
    int probability;
    private static final long serialVersionUID = 77L;

    public NPC(EntityBuilder parameters) {
        super(parameters);
    }

    protected NPC() {
    }

    public NPC initConstructor() {
        return this;
    }

    public void inspect(Player x) {
        Room now = x.getLocation();
        Entity npc = now.getNpc();
        String name = npc.getName();
        System.out.println("You see a " + name + "");
        if (npc.health <= 0) {
            System.out.println("It's system components are not active\n ");
        }
    }

    public void interact(Player a) {
        Dialogue.settingContactNpc(a);
    }

    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        victim.setHealth(victim.getHealth() - attacker.getDamage());
    }

    public int getProbability() {
        return probability;
    }

}

