package nl.rug.oop.rpg;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Inventory extends AvatarInterface {


    public Inventory(PlayerInterface parameters) {
        super(parameters);
    }

    public Inventory() {
        super();
    }

    public Inventory generateInv() {
        HashMap<String, Weapons> wlist = new HashMap<>();
        HashMap<String, Consumables> clist = new HashMap<>();
        return new PlayerInterface().
                weaponInv(wlist).
                consumInv(clist).
                createInv();
    }

    static class InventoryCheck implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            String input;
            Room r = x.location;
            EnumMap<InventoryOpt, String> invopt = InventoryOpt.getInv();
            do {
                invopt.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back"));
        }
    }

    static class InventoryInteract {

        static class getInvWeapons implements GameCommands {

            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Typewriter tw = new Typewriter();
                tw.type("-Weapons---------------\n");
                x.inventory.wList.keySet().forEach(System.out::println);
                tw.type("-----------------------\n");
                tw.type("(w) Interact \n (back) Return \n");
                String input = in.nextLine();
                if (input.equals("back")) {
                    return;
                }
                HashMap<String, GameCommands> cmenu = menuTr.submenus.get("e").submenus.get(input).menunode;
                Item item =  x.hold.SelectItem(x.inventory.wList, in);
                renderMenu(x, in, cmenu, menuTr, item);
            }
        }

        static class getInvConsum implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Typewriter tw = new Typewriter();
                tw.type("-Consumables-----------\n");
                x.inventory.cList.keySet().forEach(System.out::println);
                tw.type("-----------------------\n");
                tw.type("(c) Interact \n (back) Return \n");
                String input = in.nextLine();
                if (input.equals("back")) {
                    return;
                }
                HashMap<String, GameCommands> cmenu = menuTr.submenus.get("e").submenus.get(input).menunode;
                Item item =  x.hold.SelectItem(x.inventory.cList, in);
                renderMenu(x, in, cmenu, menuTr, item);
            }
        }

        public static void renderMenu(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr, Item item) {
            GameCommands option;
            String input;
            Room r = x.location;
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
