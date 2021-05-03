package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.lang.reflect.*;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class InventoryInteraction extends WorldInteraction implements InventoryCommands {
    public InventoryInteraction() {
    }

    public void inventoryCheck(Player x) {
        WorldInteraction winter = new WorldInteraction();
        Scanner in = x.getRdtxt();
        Method option;
        String input;
        Room r = x.getLocation();
        EnumMap<InventoryOpt, String> invopt = InventoryOpt.getInv();
        do {
            invopt.values().forEach(System.out::println);
            input = in.nextLine();
            option =  x.getmTree().getSubmenus().get("e").getMenunode().get(input);
            try {
                option.invoke(winter, x);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } while (!input.equals("back"));
    }

    public void getInvWeapons(Player x) {
        Scanner in = x.getRdtxt();
        Typewriter tw = new Typewriter();
        tw.type("-Weapons---------------\n");
        x.getInventory().getwList().keySet().forEach(System.out::println);
        tw.type("-----------------------\n");
        tw.type("(w) Interact \n (back) Return \n");
        String input = in.nextLine();
        if (input.equals("back")) {
            return;
        }
        HashMap<String, Method> cmenu = x.getmTree().getSubmenus().get("e").getSubmenus().get(input).getMenunode();
        Item item = x.getHold().SelectItem(x.getInventory().getwList(), in);
       // renderMenu(x, in, cmenu, menuTr, item);
    }

    public void getInvConsum(Player x) {
        Scanner in = x.getRdtxt();
        Typewriter tw = new Typewriter();
        tw.type("-Consumables-----------\n");
        x.getInventory().getcList().keySet().forEach(System.out::println);
        tw.type("-----------------------\n");
        tw.type("(c) Interact \n (back) Return \n");
        String input = in.nextLine();
        if (input.equals("back")) {
            return;
        }
        HashMap<String, Method> cmenu = x.getmTree().getSubmenus().get("e").getSubmenus().get(input).getMenunode();
        Item item = x.getHold().SelectItem(x.getInventory().getcList(), in);
       // renderMenu(x, in, cmenu, menuTr, item);
    }
}
