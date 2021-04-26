package nl.rug.oop.rpg;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Combat {

    public void duel(Player x, NPC foe, HashMap<String, Commands> cmenu, Scanner in, MenuTree mtree) {
        String input;
        Commands option;
        EnumMap<Cmbtoptions,String> combopt = Cmbtoptions.setMoves();
        while (foe.health > 0 && x.health > 0) {
            System.out.println("< ╬ > System Health Status ::: " +
                    x.name + ": " + x.health + "  ▓▓▓▓   " + foe.name + ": " + foe.health);
        printFightmenu(combopt);
        option = cmenu.get(in.nextLine());
        option.exec(x,in,cmenu,mtree);
        }
        if (foe.health <= 0) {
            x.location.company = false;
            System.out.println(foe.name + "was eliminated successfully! \n Proceeding further.\n");
        } else {
            System.out.println(foe.name + "eliminated your gestalt image. \n Your avatar archive is being returned to the nearest polis checkpoint server.");
            System.exit(0);
        }
    }

    public void printFightmenu(EnumMap<Cmbtoptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }
}
