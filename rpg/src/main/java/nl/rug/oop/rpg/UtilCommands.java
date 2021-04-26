package nl.rug.oop.rpg;

import java.util.*;

public abstract class UtilCommands {
    public abstract void exec();

    static class Roomcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
            Room r = x.location;
            r.inspect(r);
        }
    }

    static class Doorcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
            Commands option;
            Room r = x.location;
            Door inst = r.doors.get(0);
            inst.inspect(r);
            option = menu.get(in.nextLine());
            option.exec(x, in, menu, menuTr);
        }
    }

    static class Npccheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
            Commands option;
            String input;
            Room r = x.location;
            r.npc.inspect(r);
            EnumMap<Npcinteract, String> npcint = Npcinteract.getCompany();
            npcint.values().forEach(System.out::println);
            do {
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back")&&r.npc.health>0);
        }
    }

    static class enterDoor implements Commands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
            System.out.println(": Choose a door :");
            telePort(x, x.location.doors.get(0), in.nextInt() - 1);
        }

        public void telePort(Player x, Door inst, int n) {
            inst.interact(x, n);
        }
    }

    static class Ncpinteraction {
        static class Npcconv implements Commands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                System.out.println("Hey, unknown entity, are you friendly?");
                lifeCheck(x);
            }
        }

        static class Npcatt implements Commands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                if(lifeCheck(x)) {
                    return;
                }
                System.out.println(" x) Confirm attack \n (back) Return");
                String input = in.nextLine();
                if(input.equals("back")) {
                    return;
                }
                HashMap<String, Commands> cmenu = menuTr.submenus.get("c").submenus.get(input).menunode;
                Combat initFight = new Combat();
                initFight.duel(x, x.location.npc, cmenu,in, menuTr );
            }

            static class Attack implements Commands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                    System.out.println("Engaging enemy. . .\n ");
                    NPC foe = x.location.npc;
                    foe.health = foe.health - x.damage;
                    x.health = x.health - foe.damage;
                }
            }

            static class Defend implements Commands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                    NPC foe = x.location.npc;
                    foe.health--;
                    x.health = x.health - foe.damage / 3;
                }
            }

            static class Flee implements Commands {
                @Override
                public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                    x.location = x.location.doors.get(0).exit;
                }
            }
        }

        static class Npctrade implements Commands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
                System.out.println("Wanna trade? ");
            }
        }
        public static boolean lifeCheck(Player x) {
            if (x.location.npc.health<=0) {
                System.out.println("Its avatar is not functioning. It is not responding.\n");
                return true;
            } else {
                return false;
            }
        }
    }


    static class Inventorycheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {

        }
    }

    static class goBack implements Commands {
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
        }
    }

    static class Itemcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr) {
            Room r = x.location;
            // Item loot = r.loot; item hierarchy
        }
    }

    public static void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.name = in.nextLine();
    }

    public static HashMap<String, Commands> setMainCommands() {
        HashMap<String, Commands> explcomm = new HashMap<>();
        explcomm.put("a", new Roomcheck()); //get methods here???
        explcomm.put("b", new Doorcheck());
        explcomm.put("c", new Npccheck());
        explcomm.put("d", new Itemcheck());
        explcomm.put("e", new Inventorycheck());
        return explcomm;
    }

    public static HashMap<String, Commands> setDoorCommands() {
        HashMap<String, Commands> doorcomm = new HashMap<>();
        doorcomm.put("y", new enterDoor());
        doorcomm.put("Y", new enterDoor());
        doorcomm.put("back", new goBack());
        return doorcomm;
    }

    public static HashMap<String, Commands> setNpcCommands() {
        HashMap<String, Commands> npccomm = new HashMap<>();
        npccomm.put("a", new Ncpinteraction.Npcconv());
        npccomm.put("b", new Ncpinteraction.Npctrade());
        npccomm.put("c", new Ncpinteraction.Npcatt());
        npccomm.put("back", new goBack());
        return npccomm;
    }

    public static HashMap<String, Commands> setCombatCommands() {
        HashMap<String, Commands> npccomm = new HashMap<>();
        npccomm.put("a", new Ncpinteraction.Npcatt.Attack());
        npccomm.put("b", new Ncpinteraction.Npcatt.Defend());
        npccomm.put("c", new Ncpinteraction.Npcatt.Flee());
        return npccomm;
    }

    public static MenuTree buildMenuTree() {
        HashMap<String, Commands> explcomm = setMainCommands();
        HashMap<String, Commands> doorcomm = setDoorCommands();
        HashMap<String, Commands> npccomm = setNpcCommands();
        HashMap<String, Commands> combatcomm = setCombatCommands();
        MenuTree rootmenu = new MenuTree(null, explcomm, null);
        MenuTree doormenu = new MenuTree(rootmenu, doorcomm, null);
        MenuTree npcmenu = new MenuTree(rootmenu, npccomm, null); //changelater
        MenuTree combatmenu = new MenuTree(rootmenu, combatcomm, null);
        HashMap<String, MenuTree> rootSubMenus = new HashMap<>();
        rootSubMenus.put("a", rootmenu);
        rootSubMenus.put("b", doormenu);
        rootSubMenus.put("c", npcmenu);
        HashMap<String, MenuTree> npcCsubMenu = new HashMap<>();
        npcCsubMenu.put("x", combatmenu);
        npcCsubMenu.put("back", rootmenu);
        npcmenu.submenus = npcCsubMenu;
        MenuTree trmenu = new MenuTree(rootmenu, explcomm, rootSubMenus);
        trmenu.root.menunode = explcomm;
        return trmenu;
    }
}
