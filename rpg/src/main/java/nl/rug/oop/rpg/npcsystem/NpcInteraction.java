package nl.rug.oop.rpg.npcsystem;

import java.util.*;

import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;


public class NpcInteraction extends WorldInteraction implements PlayerNpcAction {

    public NpcInteraction() {
    }

    /**
     * @param player
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

    public void conversePlayer(Player player) {
        player.setIntent("converse");
        player.getNpcFocus().interact(player);
    }

    public void tradePlayer(Player player) {
        player.setIntent("trade");
        player.getNpcFocus().interact(player);
    }

    public void attackPlayer(Player player) {
        Dialogue.notifyAttackPlayer(player);
        NPC foe = player.getNpccontact();
        foe.receiveAttack(player, foe);
        player.receiveAttack(foe, player);
    }

    public void defendPlayer(Player player) {
        Dialogue.notifyDefendPlayer(player);
        Entity foe = player.getLocation().getNpc();
        foe.setHealth(foe.getHealth() - 1);
        player.setHealth(player.getHealth() - foe.getDamage() / 3);
    }

    public void fleePlayer(Player player) {
        player.setLocation(player.getLocation().getDoors().get(0).getExit());
        player.setNpccontact(player.getLocation().getNpc());
        player.setFlee(true);
    }


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
