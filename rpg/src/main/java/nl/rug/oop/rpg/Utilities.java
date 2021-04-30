package nl.rug.oop.rpg;

import java.util.*;

public abstract class Utilities {
    public abstract void exec();

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


    static class Inventorycheck implements GameCommands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {

        }
    }

    static class goBack implements GameCommands {
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
        }
    }


    static class ItemCheck implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            String input;
            Room r = x.location;
            Item item = r.loot;
            EnumMap<ItemOptions, String> iteminter = ItemOptions.getItem();
            do {
                iteminter.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back") && !item.name.equals("nothing"));
        }
    }

    static class ItemInteraction {

        static class ItemInspect implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Room r = x.location;
                r.loot.inspect(r);
            }
        }

        static class ItemPick implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Room r = x.location;
                r.loot.interact(x, 0);
            }
        }

        static class ItemConsume implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                Room r = x.location;
                x.hold = r.loot;
                r.loot.Recycle(x);

            }
        }
    }


    public static void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.name = in.nextLine();
    }

    public static HashMap<String, GameCommands> setMainCommands() {
        HashMap<String, GameCommands> explcomm = new HashMap<>();
        explcomm.put("a", new Roomcheck()); //get methods here???
        explcomm.put("b", new Doorcheck());
        explcomm.put("c", new Npccheck());
        explcomm.put("d", new ItemCheck());
        explcomm.put("e", new Inventorycheck());
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
        itemcomm.put("a", new ItemInteraction.ItemInspect());
        itemcomm.put("b", new ItemInteraction.ItemConsume());
        itemcomm.put("c", new ItemInteraction.ItemPick());
        itemcomm.put("back", new goBack());
        return itemcomm;
    }

    public static MenuTree buildMenuTree() {
        HashMap<String, GameCommands> explcomm = setMainCommands();
        HashMap<String, GameCommands> doorcomm = setDoorCommands();
        HashMap<String, GameCommands> npccomm = setNpcCommands();
        HashMap<String, GameCommands> combatcomm = setCombatCommands();
        HashMap<String, GameCommands> itemcomm = setItemCommands();
        MenuTree rootmenu = new MenuTree(null, explcomm, null);
        MenuTree doormenu = new MenuTree(rootmenu, doorcomm, null);
        MenuTree npcmenu = new MenuTree(rootmenu, npccomm, null); //changelater
        MenuTree combatmenu = new MenuTree(rootmenu, combatcomm, null);
        MenuTree itemenu = new MenuTree(rootmenu, itemcomm, null);
        HashMap<String, MenuTree> rootSubMenus = new HashMap<>();
        rootSubMenus.put("a", rootmenu);
        rootSubMenus.put("b", doormenu);
        rootSubMenus.put("c", npcmenu);
        rootSubMenus.put("d", itemenu);
        HashMap<String, MenuTree> npcCsubMenu = new HashMap<>();
        npcCsubMenu.put("x", combatmenu);
        npcCsubMenu.put("back", rootmenu);
        npcmenu.submenus = npcCsubMenu;
        MenuTree trmenu = new MenuTree(rootmenu, explcomm, rootSubMenus);
        trmenu.root.menunode = explcomm;
        return trmenu;
    }
}
