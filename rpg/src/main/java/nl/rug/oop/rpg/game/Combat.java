package nl.rug.oop.rpg.game;

import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.menu.options.CombatMenuOptions;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.util.EnumMap;
import java.util.Scanner;

public class Combat {
    /**
     * The method is used to conduct the combat process;
     * @param player
     * @param foe NPC subclass
     * @param mtree Menu Tree for the combat menu
     */
    public void duelProcess(Player player, NPC foe, MenuTree mtree) {
        String input;
        Scanner in = player.getRdtxt();
        Method option;
        EnumMap<CombatMenuOptions, String> combopt = CombatMenuOptions.setMoves();
        player.setHostile(true);
        do {
            Dialogue.printObnoxiousCombatMenu(player, foe);
            printFightMenu(combopt);
            input = in.nextLine();
            if (player.isForcedcomb() && input.equals("c")) {
                Dialogue.notifyFfC(player);
                continue;
            }
            option = mtree.getMenunode().get(input);
            Dialogue.combatLog();
            try {
                Object interact = WorldInteraction.getActionType(option);
                option.invoke(interact, player);
            } catch (Exception e) {
                player.getTw().type("No such option\n");
            }
            Dialogue.combatLog();
            checkCombat(player, foe);
        } while (foe.getHealth() > 0 && player.getHealth() > 0 && (!input.equals("c") || player.isForcedcomb()));
        duelConclusion(player, foe);
    }

    /**
     * Updating player field values based on the outcome of the duelProcess method.
     * @param player
     * @param foe
     */
    public void duelConclusion(Player player, NPC foe) {
        if (foe.getHealth() <= 0) {
            player.getLocation().setCompany(false);
            Dialogue.notifySuccess(player, foe);
            switchMenu(player);
        } else if (player.getHealth() <= 0) {
            Dialogue.notifyCessation(player, foe);
            System.exit(0);
        } else if (player.isFlee()) {
            player.getTw().type("Fleeing combat & exiting room\n");
            player.setFlee(false);
            switchMenu(player);
        }
    }

    public void printFightMenu(EnumMap<CombatMenuOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void checkCombat(Player player, NPC foe) {
        if (foe.getHealth() < 0 || player.getHealth() < 0) {
            player.setForcedcomb(false);
        }
    }

    public void switchMenu(Player x) {
        x.setmTree(x.getmTree().getRoot().getRoot());
    }
}
