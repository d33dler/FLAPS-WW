package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.savingsys.SavingSystem;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;

public class GameMenu {
    public void fetchMenu(Player player)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            IOException,
            ClassNotFoundException {
        Method option;
        String input;
        SavingSystem saves = new SavingSystem();

        do {
            player = saves.updatePlayer(player);
            MenuTree menuTree = player.getmTree();
            EnumMap<?, String> options = menuTree.getOptionlist();
            if (options != null) {
                options.values().forEach(System.out::println);
            }
            input = player.getRdtxt().nextLine();
            player.setSinput(input);
            option = menuTree.getMenunode().get(input);
            if (menuTree.getSubmenus() != null && menuTree.getSubmenus().get(input) != null) {
                player.setmTree(menuTree.getSubmenus().get(input));
            }
            try {
                Object interact = WorldInteraction.getActionType(option);
                option.invoke(interact, player);
            } catch (NullPointerException e) {
                System.out.println("No such option\n");
            }
        } while (!input.equals("exit sim"));
    }

    public void switchSaveMenu(Player player) {
    }

}
