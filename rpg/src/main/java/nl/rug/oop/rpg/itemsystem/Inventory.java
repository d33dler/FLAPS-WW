package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.usersystem.AvatarInterface;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.usersystem.PlayerInterfaceBuilder;
import nl.rug.oop.rpg.worldsystem.Room;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Inventory extends AvatarInterface {

    public Inventory(PlayerInterfaceBuilder parameters) {
        super(parameters);
    }

    public Inventory() {
        super();
    }

    public Inventory generateInv() {
        HashMap<String, Weapons> wlist = new HashMap<>();
        HashMap<String, Consumables> clist = new HashMap<>();
        return new PlayerInterfaceBuilder().
                weaponInv(wlist).
                consumInv(clist).
                createInv();
    }

    public static class InventoryCheck implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            String input;
            Room r = x.getLocation();
            EnumMap<InventoryOpt, String> invopt = InventoryOpt.getInv();
            do {
                invopt.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back"));
        }
    }

    public static class InventoryInteract {

        public static class getInvWeapons implements GameCommands {

            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Typewriter tw = new Typewriter();
                tw.type("-Weapons---------------\n");
                x.getInventory().wList.keySet().forEach(System.out::println);
                tw.type("-----------------------\n");
                tw.type("(w) Interact \n (back) Return \n");
                String input = in.nextLine();
                if (input.equals("back")) {
                    return;
                }
                HashMap<String, GameCommands> cmenu = menuTr.getSubmenus().get("e").getSubmenus().get(input).getMenunode();
                Item item =  x.getHold().SelectItem(x.getInventory().wList, in);
                renderMenu(x, in, cmenu, menuTr, item);
            }
        }

        public static class getInvConsum implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Typewriter tw = new Typewriter();
                tw.type("-Consumables-----------\n");
                x.getInventory().cList.keySet().forEach(System.out::println);
                tw.type("-----------------------\n");
                tw.type("(c) Interact \n (back) Return \n");
                String input = in.nextLine();
                if (input.equals("back")) {
                    return;
                }
                HashMap<String, GameCommands> cmenu = menuTr.getSubmenus().get("e").getSubmenus().get(input).getMenunode();
                Item item =  x.getHold().SelectItem(x.getInventory().cList, in);
                renderMenu(x, in, cmenu, menuTr, item);
            }
        }

        public static void renderMenu(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr, Item item) {
            GameCommands option;
            String input;
            Room r = x.getLocation();
            EnumMap<InvConsumOptions, String> invopt = InvConsumOptions.getSubInv();
            do {
                invopt.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back"));
        }

    }
}
