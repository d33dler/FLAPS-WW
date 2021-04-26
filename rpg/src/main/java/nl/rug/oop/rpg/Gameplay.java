package nl.rug.oop.rpg;

import java.util.*;

public class Gameplay {

    public void Launch() {
        String name = "user";
        List<Item> inv = new ArrayList<>();//inventory system
        //script intro methods go here
        //intro dialogue tree (optional)
        World morph = new World();
        World map = morph.createMap();
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Player player = new InitEntity()
                .name(name)
                .hdm(100,10,30)
                .inv(inv)
                .loc(listrm.get(0))
                .protagonist();
        Explore(map, player, listrm);
    }

    public void Explore(World map, Player player, List<Room> listrm) {
        EnumMap<Explrptions, String> exoptns = Explrptions.getExmn();
        HashMap<String, Commands> comms = new HashMap<>();
        setComms(comms);
        String input = "y";
        Scanner rdtxt = new Scanner(System.in);
        Commands option;
        System.out.println("You are: " + player.name);
        printExpmenu(exoptns);
        while (!input.equals("exit sim")) {
            input = rdtxt.nextLine();
            option = comms.get(input);
            if (option != null) {
                option.exec(player);
                printExpmenu(exoptns);
            }
        }
        rdtxt.close();
    }

    public void printExpmenu(EnumMap<Explrptions, String> exoptns) {
    exoptns.values().forEach(System.out::println);
    }

    public void setComms(HashMap<String, Commands> comms) {
        comms.put("a", new Roomcheck()); //get methods here???
        comms.put("b", new Doorcheck());
        comms.put("c", new Npccheck());
        comms.put("d", new Itemcheck());
        comms.put("e", new Inventorycheck());
    }

}

class Roomcheck implements Commands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        r.inspect(r);
    }
}

class Doorcheck implements Commands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        Door inst = r.doors.get(0);
        inst.inspect(r);
        inst.interact(x);
    }
}


class Itemcheck implements Commands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        // Item loot = r.loot; item hierarchy
    }
}

class Npccheck implements Commands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        r.npc.inspect(r);
        EnumMap<Npcinteract,String> npcint = Npcinteract.getCompany();

    }
}

class Npcconv implements Commands {
    @Override
    public void exec(Player x) {

    }
}
class Npcatt implements Commands {
    @Override
    public void exec(Player x) {

    }
}
class Npctrade implements Commands {
    @Override
    public void exec(Player x) {

    }
}
class Inventorycheck implements Commands {

    @Override
    public void exec(Player x) {

    }
}