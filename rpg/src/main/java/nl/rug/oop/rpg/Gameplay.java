package nl.rug.oop.rpg;

import java.util.*;
import java.util.stream.Stream;

public class Gameplay {

    public void Launch() {
        Player player = new Player();
        Mainmenu start = new Mainmenu();
        //script intro methods go here
        //intro dialogue tree (optional)
        World morph = new World();
        World map = morph.createMap();
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Explore(map, player, listrm);
    }

    public void Explore(World map, Player player, List<Room> listrm) {
        EnumMap<Explrptions, String> exoptns = Explrptions.getExmn();
        HashMap<String, Mcommands> comms = new HashMap<>();
        setComms(comms);
        player.location = listrm.get(0);
        Room r = listrm.get(0);
        String input = "y";
        Scanner rdtxt = new Scanner(System.in);
        Mcommands pick;
        printExpmenu(exoptns);
        while (!input.equals("exit")) {
            input = rdtxt.nextLine();
            pick = comms.get(input);
            if (pick != null) {
                pick.exec(player);
                printExpmenu(exoptns);
            }
        }
        rdtxt.close();
    }

    public void printExpmenu(EnumMap<Explrptions, String> exoptns) {
        System.out.println(exoptns.values()); //fix brackets
    }

    public void setComms(HashMap<String, Mcommands> comms) {
        comms.put("a", new Roomcheck());
        comms.put("b", new Doorcheck());
        comms.put("c", new Npccheck());
        comms.put("d", new Itemcheck());
        comms.put("e", new Inventorycheck());
    }

}

class Roomcheck implements Mcommands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        r.inspect(r);
    }
}

class Doorcheck implements Mcommands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        Door inst = r.doors.get(0);
        inst.inspect(r);
        inst.interact(x);
    }
}


class Itemcheck implements Mcommands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        // Item loot = r.loot; item hierarchy
    }
}

class Npccheck implements Mcommands {

    @Override
    public void exec(Player x) {
        Room r = x.location;
        // r.npc.inspect(); enum use to create instance npc
    }
}

class Inventorycheck implements Mcommands {

    @Override
    public void exec(Player x) {

    }
}