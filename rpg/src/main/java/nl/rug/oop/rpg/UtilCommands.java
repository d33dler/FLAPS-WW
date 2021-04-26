package nl.rug.oop.rpg;
import java.awt.*;
import java.util.*;
import nl.rug.oop.rpg.MenuTree;

public abstract class UtilCommands {
    public abstract void exec();


    static class Roomcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
            Room r = x.location;
            r.inspect(r);
        }
    }

    static class Doorcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
           // HashMap<String, Commands> doorcomm = setDoorCommands();
            Commands option;
            Room r = x.location;
            Door inst = r.doors.get(0);
            inst.inspect(r);
            option = menu.get(in.nextLine());
            option.exec(x, in, menu);
        }
    }

    static class Npccheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
           // HashMap<String, Commands> npccomm = UtilCommands.setNpcCommands();
            Room r = x.location;
            r.npc.inspect(r);
            EnumMap<Npcinteract, String> npcint = Npcinteract.getCompany();
            npcint.values().forEach(System.out::println);

        }
    }

    static class enterDoor implements Commands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
            System.out.println(": Choose a door :");
            telePort(x, x.location.doors.get(0), in.nextInt() - 1);
        }

        public void telePort(Player x, Door inst, int n) {
            inst.interact(x, n);
        }
    }

    static class Npcconv implements Commands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {

        }
    }

    static class Npcatt implements Commands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {

        }
    }

    static class Npctrade implements Commands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {

        }
    }

    static class Inventorycheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {

        }
    }

    static class goBack implements Commands {
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
        }
    }

    static class Itemcheck implements Commands {

        @Override
        public void exec(Player x, Scanner in, HashMap<String, Commands> menu) {
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
        HashMap<String, Commands > doorcomm = new HashMap<>();
        doorcomm.put("y", new enterDoor());
        doorcomm.put("Y", new enterDoor());
        doorcomm.put("back", new goBack());
        return doorcomm;
    }

    public static HashMap<String, Commands> setNpcCommands() {
        HashMap<String, Commands> npccomm = new HashMap<>();
        npccomm.put("a", new Npcconv());
        npccomm.put("b", new Npcatt());
        npccomm.put("c", new Npctrade());
        npccomm.put("back", new goBack());
        return npccomm;
    }

    public static MenuTree buildMenuTree() {
        HashMap<String, Commands> explcomm = setMainCommands();
        HashMap<String, Commands > doorcomm = setDoorCommands();
        HashMap<String, Commands> npccomm = setNpcCommands();
        MenuTree rootmenu = new MenuTree(null,explcomm,null);
        MenuTree doormenu = new MenuTree(rootmenu,doorcomm,null);
        MenuTree npcmenu = new MenuTree(rootmenu,npccomm,null); //changelater
        HashMap<String, MenuTree> submenus =  new HashMap<>();
        submenus.put("a", rootmenu);
        submenus.put("b", doormenu );
        submenus.put("c", npcmenu);
        MenuTree trmenu = new MenuTree(rootmenu,explcomm,submenus);
        trmenu.root.menunode = explcomm;
        return trmenu;
    }

}
