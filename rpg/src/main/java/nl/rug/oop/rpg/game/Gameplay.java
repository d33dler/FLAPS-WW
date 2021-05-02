package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.menu.MenuBuilder;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.ExploreOptions;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

import java.util.*;


public class Gameplay {

    public void Launch(Scanner input) {
        Dialogue intro = new Dialogue();
       // intro.Comunication();
        World morph = new World();
        World map = morph.createMap();
        Player player = morph.generatePlayer(map);
        Explore(player, input);
    }

    public void Explore(Player player, Scanner txtIn) {
        player.getUserName(player, txtIn);
        MenuBuilder menubuild = new MenuBuilder();
        MenuTree trMenu = menubuild.buildMenuTree();
        HashMap<String, GameCommands> menu;
        GameCommands option;
        EnumMap<ExploreOptions, String> exoptns = ExploreOptions.getExmn();
        String input = "y";
        System.out.println("Greetings, " + player.getName() + "!\n \n");
        printExpmenu(exoptns);
        menu = trMenu.getRoot().getMenunode();
        while (!input.equals("exit sim")) {
            input = txtIn.nextLine();
            option = menu.get(input);
            if (option != null) {
                option.exec(player, txtIn, trMenu.getSubmenus().get(input).getMenunode(), trMenu);
                printExpmenu(exoptns);
            }
        }
        txtIn.close();
    }

    public void printExpmenu(EnumMap<ExploreOptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void reachSubMenu(HashMap<String, HashMap<String, GameCommands>> allMenus, String input) { //prob loop

    }


}





