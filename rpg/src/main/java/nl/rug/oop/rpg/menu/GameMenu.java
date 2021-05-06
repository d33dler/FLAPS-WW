package nl.rug.oop.rpg.menu;
import nl.rug.oop.rpg.game.configsys.Configurations;
import nl.rug.oop.rpg.game.savingsys.SavingSystem;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;
import java.lang.reflect.Method;
import java.util.EnumMap;

public class GameMenu {
    public void fetchMenu(Player player) throws Exception {
        SavingSystem saves = new SavingSystem();
        do {
            player = saves.updatePlayer(player);
            MenuTree menuTree = player.getmTree();
            Method option = fetchOptions(player, menuTree);
            try {
                Object interact = WorldInteraction.getActionType(option);
                option.invoke(interact, player);
            } catch (NullPointerException e) {
                System.out.println("No such option\n");
            }
        } while (!player.getSinput().equals("exit sim"));
    }

    public Method fetchOptions(Player player, MenuTree menuTree) {
        EnumMap<?, String> options = menuTree.getOptionlist();
        if (options != null) {
            options.values().forEach(System.out::println);
        }
        String input = player.getRdtxt().nextLine();
        player.setSinput(input);
        Method option = menuTree.getMenunode().get(input);
        if (menuTree.getSubmenus() != null && menuTree.getSubmenus().get(input) != null) {
            player.setmTree(menuTree.getSubmenus().get(input));
        }
        return option;
    }
    public void switchSaveMenu(Player player) {
    }

}
