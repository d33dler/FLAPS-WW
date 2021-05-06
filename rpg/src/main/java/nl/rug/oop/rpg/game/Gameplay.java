package nl.rug.oop.rpg.game;

import java.io.IOException;
import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.worldsystem.*;

import java.util.*;


public class Gameplay {

    public void Launch() {
        //Dialogue intro = new Dialogue();
        //intro.Comunication();
        World morph = new World();
        World map = morph.createMap();
        Player player = morph.generatePlayer(map);
        player.setMap(map);
        Explore(player);
    }

    public void Explore(Player player) {
        GameMenu gmenu = new GameMenu();
        Scanner txtIn = player.getRdtxt();
        player.getUserName(player, txtIn);
        System.out.println("Greetings, " + player.getName() + "!\n \n");
        try {
            gmenu.fetchMenu(player);
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException
                | IOException
                | ClassNotFoundException e) {
            System.out.println("Error generating exploring menu. Exiting.");
        }
    }


}





