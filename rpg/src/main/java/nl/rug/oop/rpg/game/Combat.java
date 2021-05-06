package nl.rug.oop.rpg.game;

import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.util.EnumMap;
import java.util.Scanner;

public class Combat {

    public void duel(Player x, NPC foe, MenuTree mtree) {
        String input;
        Scanner in = x.getRdtxt();
        Method option;
        EnumMap<CombatOptions, String> combopt = CombatOptions.setMoves();
        do {
            System.out.println("< ╬ > System Health Status ::: " +
                    x.getName() + ": " + x.getHealth() + "  ▓   " + foe.getName() + ": " + foe.getHealth() + " < ╬ >\n");
            printFightmenu(combopt);
            input = in.nextLine();
            option = mtree.getMenunode().get(input);
            try {
                Object interact = WorldInteraction.getActionType(option);
                option.invoke(interact, x);
            } catch (NullPointerException
                    | InvocationTargetException
                    | NoSuchMethodException
                    | InstantiationException
                    | IllegalAccessException e) {
                x.getTw().type("No such option\n");
            }
        } while (foe.getHealth() > 0 && x.getHealth() > 0 && !input.equals("c"));

        if (foe.getHealth() <= 0) {
            x.getLocation().setCompany(false);
            x.getTw().type(foe.getName() + " was eliminated successfully!\nProceeding further.\n");
            x.getTw().type(". . . .\n");
            switchMenu(x);
        } else if (x.getHealth() <= 0) {
            x.getTw().type("The " + foe.getName() + " eliminated your android's gestalt from the test environment. \n Your avatar archive is being returned to the nearest Polis checkpoint server.");
            System.exit(0);
        } else if (x.isFlee()) {
            x.getTw().type("Fleeing combat & exiting room\n");
            x.setFlee(false);
            switchMenu(x);
        }
    }

    public void printFightmenu(EnumMap<CombatOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void switchMenu(Player x) {
        x.setmTree(x.getmTree().getRoot().getRoot());
    }
}
