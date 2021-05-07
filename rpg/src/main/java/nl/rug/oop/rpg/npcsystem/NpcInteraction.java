package nl.rug.oop.rpg.npcsystem;

import java.util.*;

import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

/**
 * NpcInteraction is an extension class holding relevant methods concerning
 * the players interaction with the NPCs.
 */
public class NpcInteraction extends WorldInteraction implements PlayerNpcAction {

    public NpcInteraction() {
    }

    /**
     *  Helper method to initiate combat with NPCS
     */
    public void engageNpc(Player player) {
        Scanner in = player.getRdtxt();
        if (lifeCheck(player)) {
            return;
        }
        player.setIntent("combat");
        Dialogue.confirmCombatInit();
        player.setSinput(in.nextLine());
        if (player.getSinput().equals("back")) {
            return;
        }
        player.getNpcFocus().interact(player);
    }

    /**
     *
     * @param player gets the intent field updated and the method calls upon interact()
     *               which is overriden by NPC sublcasses - giving different interaction behaviour results.
     */
    public void conversePlayer(Player player) {
        player.setIntent("converse");
        player.getNpcFocus().interact(player);
    }
    /**
     *
     * @param player gets the intent field updated and the method calls upon interact()
     *               which is overriden by NPC sublcasses - giving different interaction behaviour results.
     */
    public void tradePlayer(Player player) {
        player.setIntent("trade");
        player.getNpcFocus().interact(player);
    }
    /**
     *
     * Helper method to conduct the attack action by calling upon the receivingAttack methods of objects
     * that are part of the combatProcess method.
     */
    public void attackPlayer(Player player) {
        Dialogue.notifyAttackPlayer(player);
        NPC foe = player.getNpccontact();
        foe.receiveAttack(player, foe);
        player.receiveAttack(foe, player);
    }
    /**
     *
     * Helper method to conduct the defend action of objects
     * that are part of the combatProcess method.
     */
    public void defendPlayer(Player player) {
        Dialogue.notifyDefendPlayer(player);
        Entity foe = player.getLocation().getNpc();
        foe.setHealth(foe.getHealth() - 1);
        player.setHealth(player.getHealth() - foe.getDamage() / 3);
    }
    /**
     *
     * Helper method to conduct the fleeing processes action of objects
     * that are part of the combatProcess method.
     */
    public void fleePlayer(Player player) {
        player.setLocation(player.getLocation().getDoors().get(0).getExit());
        player.setNpccontact(player.getLocation().getNpc());
        player.setFlee(true);
    }

    /**
     *
     *
     * @return true if the NPC is not alive (has health field value < 0 )
     */
    public boolean lifeCheck(Player player) {
        if (player.getNpcFocus().getHealth() <= 0) {
            player.getTw().poeticPause("SysCheck: ", 1500);
            player.getTw().type(" Its avatar is not functioning. It is not responding.\n");
            return true;
        } else {
            return false;
        }
    }
}
