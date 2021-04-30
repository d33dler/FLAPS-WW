package nl.rug.oop.rpg;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Combat {

    public void duel(Player x, NPC foe, HashMap<String, GameCommands> cmenu, Scanner in, MenuTree mtree) {
        String input = "user";
        GameCommands option;
        EnumMap<Cmbtoptions, String> combopt = Cmbtoptions.setMoves();
        while (foe.health > 0 && x.health > 0 && !input.equals("c")) {
            System.out.println("< ╬ > System Health Status ::: " +
                    x.name + ": " + x.health + "  ▓▓▓▓   " + foe.name + ": " + foe.health);
            printFightmenu(combopt);
            input = in.nextLine();
            option = cmenu.get(input);
            option.exec(x, in, cmenu, mtree);
        }
        if (foe.health <= 0) {
            x.location.company = false;
            System.out.println(foe.name + " was eliminated successfully! \n Proceeding further.\n");
        } else if (x.health <= 0) {
            System.out.println(foe.name + "eliminated your gestalt image. \n Your avatar archive is being returned to the nearest Polis checkpoint server.");
            System.exit(0);
        } else {
            System.out.println("Fleeing combat & exiting room\n");

        }
    }

    public void printFightmenu(EnumMap<Cmbtoptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }
}
