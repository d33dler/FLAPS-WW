package nl.rug.oop.rpg.game;
import java.lang.reflect.*;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Combat {

    public void duel(Player x, NPC foe, HashMap<String, Method> cmenu, Scanner in, MenuTree mtree)
            throws InvocationTargetException,
            IllegalAccessException,
            NoSuchMethodException,
            InstantiationException {
        String input = "user";
        Method option;
        WorldInteraction wInter = new WorldInteraction();
        EnumMap<CombatOptions, String> combopt = CombatOptions.setMoves();

        while (foe.getHealth() > 0 && x.getHealth() > 0 && !input.equals("c")) {
            System.out.println("< ╬ > System Health Status ::: " +
                    x.getName() + ": " + x.getHealth() + "  ▓▓▓▓   " + foe.getName() + ": " + foe.getHealth());
            printFightmenu(combopt);
            input = in.nextLine();
            option = cmenu.get(input);
            Object interaction = wInter.getActionType(option);
            option.invoke(interaction, x);
        }
        if (foe.getHealth() <= 0) {
            x.getLocation().setCompany(false);
            System.out.println(foe.getName() + " was eliminated successfully! \n Proceeding further.\n");
        } else if (x.getHealth() <= 0) {
            System.out.println(foe.getName() + "eliminated your gestalt image. \n Your avatar archive is being returned to the nearest Polis checkpoint server.");
            System.exit(0);
        } else {
            System.out.println("Fleeing combat & exiting room\n");

        }
    }

    public void printFightmenu(EnumMap<CombatOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }
}
