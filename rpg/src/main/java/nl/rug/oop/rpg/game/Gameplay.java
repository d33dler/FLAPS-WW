package nl.rug.oop.rpg.game;

import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.*;

import java.util.*;


public class Gameplay {

    public void Launch() {
        //Dialogue intro = new Dialogue();
        // intro.Comunication();
        World morph = new World();
        World map = morph.createMap();
        Player player = morph.generatePlayer(map);
        Explore(player);
    }

    public void Explore(Player player) {
        WorldInteraction wInter = new WorldInteraction();
        Scanner txtIn = player.getRdtxt();
        player.getUserName(player, txtIn);
        System.out.println("Greetings, " + player.getName() + "!\n \n");

        // printExpmenu(exoptns);

        try {
            renderMenu(player, txtIn, wInter);
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException e) {
            e.printStackTrace();
        }
        // txtIn.close();
    }

    public void printExpmenu(EnumMap<ExploreOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void reachSubMenu(HashMap<String, HashMap<String, Method>> allMenus, String input) { //prob loop
    }

    public void renderMenu(Player player, Scanner in, WorldInteraction wInter)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        Method option;
        String input;
        Room r = player.getLocation();
        do {
            MenuTree menuTree = player.getmTree();
            EnumMap<?, String> options = menuTree.getOptionlist();
            if (options != null) {
                options.values().forEach(System.out::println);
            }
            input = readInput(in);
            player.setInput(input);
            option = menuTree.getMenunode().get(input);
            if (menuTree.getSubmenus() != null && menuTree.getSubmenus().get(input) != null) {
                player.setmTree(menuTree.getSubmenus().get(input));
            }
            try {
                Object interact = wInter.getActionType(option);
                option.invoke(interact, player);
            } catch (NullPointerException e) {
                System.out.println("No such option\n");
            }
        } while (!input.equals("exit sim"));
    }

    public String readInput(Scanner in) {
        return in.nextLine();
    }
}





