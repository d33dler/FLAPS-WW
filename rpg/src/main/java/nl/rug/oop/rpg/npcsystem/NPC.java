package nl.rug.oop.rpg.npcsystem;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;
import java.io.Serializable;

/**
 * NPC class - implementing default behaviour for all NPC subtypes and the general constructor.
 * int probability - serves as probability value for creation of different NPC subtypes.
 */

public abstract class NPC extends Entity implements Inspectable, Interactable, Attackable, Serializable {
    int probability;
    private static final long serialVersionUID = 77L;

    /**
     *
     * @param parameters Builder pattern class delivering parameters for the NPC creation
     */
    public NPC(EntityBuilder parameters) {
        super(parameters);
    }

    /**
     * Argument free constructor
     */
    protected NPC() {
    }

    public NPC initConstructor() {
        return this;
    }

    /**
     * @param player Object is used to identify the NPC object to be inspected.
     *               Player contains NPC field for setting the NPC that is currently interacting with
     *               the player.
     */

    public void inspect(Player player) {
        Entity npc = player.getNpcFocus();
        String name = npc.getName();
        System.out.println("You see a " + name + "");
        if (npc.health <= 0) {
            System.out.println("It's system components are not active\n ");
        }
    }

    /**
     * @param player Parent class NPC interact function to execute common interaction
     *               beginning for all NPCs interaction and all interaction variants.
     */
    public void interact(Player player) {
        Dialogue.settingContactNpc(player);
    }

    /**
     * @param attacker Object that decreases the integer health field value of the victim object.
     * @param victim   Object which has its health field value updated.
     */
    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        victim.setHealth(victim.getHealth() - attacker.getDamage());
    }

    public int getProbability() {
        return probability;
    }

}

