package nl.rug.oop.rpg;

import java.util.*;

public class Gameplay {

    public void Launch() {
        Mainmenu start = new Mainmenu();

        //script intro methods go here
        //intro dialogue tree (optional)

        World morph = new World();
        World map = morph.createMap();
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Entity player = new Player() ;
        player.location = listrm.get(0);
        Room r = listrm.get(0);
        //System.out.println(" You are in a" + r.atr1.getAtt1() + "room" + r.atr2.getAtt2()); it works!
        
    }

}
