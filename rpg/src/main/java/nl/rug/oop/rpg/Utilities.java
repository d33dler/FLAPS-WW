package nl.rug.oop.rpg;

import java.util.*;

public abstract class Utilities {


    static class Roomcheck implements GameCommands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.location;
            r.inspect(r);
        }
    }

    static class Doorcheck implements GameCommands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            Room r = x.location;
            Door inst = r.doors.get(0);
            inst.inspect(r);
            option = menu.get(in.nextLine());
            option.exec(x, in, menu, menuTr);
        }
    }

    static class Npccheck implements GameCommands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            String input;
            Room r = x.location;
            r.npc.inspect(r);
            EnumMap<Npcinteract, String> npcint = Npcinteract.getCompany();
            do {
                npcint.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back") && r.npc.health > 0 && !x.flee);
            x.flee = false;
        }
    }

    static class enterDoor implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            System.out.println(": Choose a door :");
            telePort(x, x.location.doors.get(0), in.nextInt() - 1);
        }

        public void telePort(Player x, Door inst, int n) {
            inst.interact(x, n);
        }
    }

    static class Ncpinteraction {
        static class Npcconv implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                System.out.println("Hey, unknown entity, are you friendly?");
                lifeCheck(x);
            }
        }

        static class Npcatt implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                if (lifeCheck(x)) {
                    return;
                }
                System.out.println(" x) Confirm attack \n (back) Return");
                String input = in.nextLine();
                if (input.equals("back")) {
                    return;
                }
                HashMap<String, GameCommands> cmenu = menuTr.submenus.get("c").submenus.get(input).menunode;
                Combat initFight = new Combat();
                initFight.duel(x, x.location.npc, cmenu, in, menuTr);
            }

            static class Attack implements GameCommands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                    System.out.println("Engaging enemy. . .\n ");
                    NPC foe = x.location.npc;
                    foe.health = foe.health - x.damage;
                    x.health = x.health - foe.damage;
                }
            }

            static class Defend implements GameCommands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                    NPC foe = x.location.npc;
                    foe.health--;
                    x.health = x.health - foe.damage / 3;
                }
            }

            static class Flee implements GameCommands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                    x.location = x.location.doors.get(0).exit;
                    x.flee = true;
                }
            }
        }

        static class Npctrade implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                System.out.println("Wanna trade? ");
            }
        }

        public static boolean lifeCheck(Player x) {
            if (x.location.npc.health <= 0) {
                System.out.println("Its avatar is not functioning. It is not responding.\n");
                return true;
            } else {
                return false;
            }
        }
    }

    static class goBack implements GameCommands {
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
        }
    }


    public static void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.name = in.nextLine();
    }

    public static HashMap<String, GameCommands> setMainCommands() {
        HashMap<String, GameCommands> explcomm = new HashMap<>();
        explcomm.put("a", new Roomcheck());
        explcomm.put("b", new Doorcheck());
        explcomm.put("c", new Npccheck());
        explcomm.put("d", new Item.ItemCheck());
        explcomm.put("e", new Inventory.InventoryCheck());
        return explcomm;
    }

    public static HashMap<String, GameCommands> setDoorCommands() {
        HashMap<String, GameCommands> doorcomm = new HashMap<>();
        doorcomm.put("y", new enterDoor());
        doorcomm.put("Y", new enterDoor());
        doorcomm.put("back", new goBack());
        return doorcomm;
    }

    public static HashMap<String, GameCommands> setNpcCommands() {
        HashMap<String, GameCommands> npccomm = new HashMap<>();
        npccomm.put("a", new Ncpinteraction.Npcconv());
        npccomm.put("b", new Ncpinteraction.Npctrade());
        npccomm.put("c", new Ncpinteraction.Npcatt());
        npccomm.put("back", new goBack());
        return npccomm;
    }

    public static HashMap<String, GameCommands> setCombatCommands() {
        HashMap<String, GameCommands> npccomm = new HashMap<>();
        npccomm.put("a", new Ncpinteraction.Npcatt.Attack());
        npccomm.put("b", new Ncpinteraction.Npcatt.Defend());
        npccomm.put("c", new Ncpinteraction.Npcatt.Flee());
        return npccomm;
    }

    public static HashMap<String, GameCommands> setItemCommands() {
        HashMap<String, GameCommands> itemcomm = new HashMap<>();
        itemcomm.put("a", new Item.ItemInteraction.ItemInspect());
        itemcomm.put("b", new Item.ItemInteraction.ItemConsume());
        itemcomm.put("c", new Item.ItemInteraction.ItemPick());
        itemcomm.put("back", new goBack());
        return itemcomm;
    }
    public static HashMap<String, GameCommands> setInventoryCommands() {
        HashMap<String, GameCommands> inv = new HashMap<>();
        inv.put("w", new Inventory.InventoryInteract.getInvWeapons());
        inv.put("c", new Inventory.InventoryInteract.getInvConsum());
        inv.put("back", new goBack());
        return inv;
    }
    public static HashMap<String, GameCommands> setConsumInventoryCommands() {
        HashMap<String, GameCommands> invsub = new HashMap<>();
        invsub.put("a", new Item.ItemInteraction.ItemInvConsume());
        invsub.put("b", new Item.ItemInteraction.ItemInvRecycle());
        invsub.put("c", new Item.ItemInteraction.ItemInvThrow());
        invsub.put("back", new goBack());
        return invsub;
    }
    public static HashMap<String, GameCommands> setWeaponInventoryCommands() {
        HashMap<String, GameCommands> invsub = new HashMap<>();
        invsub.put("a", new Item.ItemInteraction.ItemInvEquip());
        invsub.put("b", new Item.ItemInteraction.ItemInvRecycle());
        invsub.put("c", new Item.ItemInteraction.ItemInvThrow());
        invsub.put("back", new goBack());
        return invsub;
    }
    public static MenuTree buildMenuTree() {
        HashMap<String, GameCommands> explcomm = setMainCommands();
        HashMap<String, GameCommands> doorcomm = setDoorCommands();
        HashMap<String, GameCommands> npccomm = setNpcCommands();
        HashMap<String, GameCommands> combatcomm = setCombatCommands();
        HashMap<String, GameCommands> itemcomm = setItemCommands();
        HashMap<String, GameCommands> invcomm = setInventoryCommands();
        HashMap<String, GameCommands> invconsomm = setConsumInventoryCommands();
        HashMap<String, GameCommands> invwepcomm = setWeaponInventoryCommands();
        MenuTree rootmenu = new MenuTree(null, explcomm, null);
        MenuTree doormenu = new MenuTree(rootmenu, doorcomm, null);
        MenuTree npcmenu = new MenuTree(rootmenu, npccomm, null);
        MenuTree combatmenu = new MenuTree(npcmenu, combatcomm, null);
        MenuTree itemenu = new MenuTree(rootmenu, itemcomm, null);
        MenuTree invmenu = new MenuTree(rootmenu, invcomm,null);
        MenuTree invconsmenu = new MenuTree(invmenu, invconsomm,null);
        MenuTree invwepmenu = new MenuTree(invmenu, invwepcomm,null);
        HashMap<String, MenuTree> rootSubMenus = new HashMap<>();
        rootSubMenus.put("a", rootmenu);
        rootSubMenus.put("b", doormenu);
        rootSubMenus.put("c", npcmenu);
        rootSubMenus.put("d", itemenu);
        rootSubMenus.put("e", invmenu);
        HashMap<String, MenuTree> npcCsubMenu = new HashMap<>();
        HashMap<String,MenuTree> invSubMenu = new HashMap<>();
        invSubMenu.put("w", invconsmenu );
        invSubMenu.put("c", invwepmenu );
        invSubMenu.put("back", rootmenu );
        npcCsubMenu.put("x", combatmenu);
        npcCsubMenu.put("back", rootmenu);
        invmenu.submenus = invSubMenu;
        npcmenu.submenus = npcCsubMenu;
        MenuTree trmenu = new MenuTree(rootmenu, explcomm, rootSubMenus);
        trmenu.root.menunode = explcomm;
        return trmenu;
    }
}
