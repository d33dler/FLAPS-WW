package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;

public class GameMenu {
    public void renderMenu(Player player)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        Method option;
        String input;
        WorldInteraction wi = player.getWinter();
        do {
            MenuTree menuTree = getMenu(player);
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
        player.setSavemenu(!player.isSavemenu());

    }

    public MenuTree getMenu(Player player) {
        if (player.isSavemenu()) {
            return player.getsMenu();
        } else {
            return player.getmTree();
        }
    }
}
