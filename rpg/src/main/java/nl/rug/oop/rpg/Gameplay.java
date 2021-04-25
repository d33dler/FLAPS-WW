package nl.rug.oop.rpg;

import java.util.*;

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
        player.location = listrm.get(0);
        Room r = listrm.get(0);
        Scanner rdtxt = new Scanner(System.in);
        Room check;
        do {
            System.out.println(" You are in a" + r.atr1.getAtt1() + "room" + r.atr2.getAtt2());
            System.out.println(" There are " + r.cdors + " doors here ");
            for (int i = 0; i < r.doors.size(); i++) {
                System.out.println(" Door " + (i + 1) + " is " + r.doors.get(i).color);
            }
            System.out.println(" Pick one");
            int input = Integer.parseInt(rdtxt.nextLine());
            check = r.doors.get(input).enter;
            if(!check.id.equals(r.id)) {
                r = r.doors.get(input).enter;
            } else {
                r = r.doors.get(input).exit;
            }
        } while (rdtxt.hasNextLine());
        rdtxt.close();

        for (int i = 0; i < r.doors.size(); i++) {
            System.out.println(" Door " + (i + 1) + " is " + r.doors.get(i).color);
            Room nxt = r.doors.get(i).enter;
            System.out.println(" Checking out: You see a " + nxt.atr1.getAtt1() + " room " + nxt.atr2.getAtt2());
            System.out.println(" THis room has " + nxt.cdors + "doors");
        }
    }

    public void printRoom(){

    }
}
