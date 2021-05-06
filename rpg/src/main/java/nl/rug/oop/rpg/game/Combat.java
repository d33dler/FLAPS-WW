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

    public void duel(Player x, NPC foe, MenuTree mtree) {
        String input;
        Scanner in = x.getRdtxt();
        Method option;
        EnumMap<CombatMenuOptions, String> combopt = CombatMenuOptions.setMoves();
        x.setHostile(true);

        do {
            printObnoxiousText(x,foe);
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

    public void printFightmenu(EnumMap<CombatMenuOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }
    public void printObnoxiousText(Player x, NPC foe){
        System.out.println("< ╬ >  :::::::::::: SysHealth :::::::::::: < ╬ >\n" + spL("", 8) +
                x.getName() + ": " + x.getHealth() + " ::: " + foe.getName() + ": " + foe.getHealth());
        System.out.println("< ╬ >  ::::::::::::   Damage  :::::::::::: < ╬ >\n" + spL("", 8) +
                spL(String.valueOf(x.getDamage()), x.getName().length()) +
                " ::: " + spL(String.valueOf(foe.getDamage()), foe.getName().length()));
    }
    public static String spL(String str, int len) {
        return String.format("%1$" + (len + 5) + "s", str);
    }

    public void switchMenu(Player x) {
        x.setmTree(x.getmTree().getRoot().getRoot());
    }
}
