package nl.rug.oop.rpg.game;

import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.ExploreOptions;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.util.*;


public class Gameplay {

    public void Launch() {
        //Dialogue intro = new Dialogue();
        // intro.Comunication();
        World morph = new World();
        World map = morph.createMap();
        Player player = morph.generatePlayer(map);
        try {
            Explore(player);
        } catch (InvocationTargetException |
                IllegalAccessException |
                NoSuchMethodException |
                InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void Explore(Player player)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        WorldInteraction wInter = new WorldInteraction();
        Scanner txtIn = player.getRdtxt();
        player.getUserName(player, txtIn);
        MenuTree trMenu = player.getmTree();
        HashMap<String, Method> menu;
        Method option;
        EnumMap<ExploreOptions, String> exoptns = ExploreOptions.getExmn();
        String input = "y";
        System.out.println("Greetings, " + player.getName() + "!\n \n");
        printExpmenu(exoptns);
        menu = trMenu.getRoot().getMenunode();

        while (!input.equals("exit sim")) {
            input = txtIn.nextLine();
            option = menu.get(input);
            player.setNpccontact(player.getLocation().getNpc());
            Object interact = wInter.getActionType(option);
            option.invoke(interact, player);
            printExpmenu(exoptns);
        }
        txtIn.close();
    }

    public void printExpmenu(EnumMap<ExploreOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }



    public void reachSubMenu(HashMap<String, HashMap<String, Method>> allMenus, String input) { //prob loop

    }


}





