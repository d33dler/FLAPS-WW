package nl.rug.oop.rpg.game;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Gameplay;
import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.menu.builders.MainMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

public class RPG {
    /**
     * Initiates the Main menu and creates the player object.
     *
     * @throws NoSuchMethodException if the Menu tree structure is not fetched successfully, or
     * method object called is not part of the menu tree hashmap of commands.
     */
    public void MainMenu() throws NoSuchMethodException {
        MenuTree mtree = new MainMenuBuilder().setMainCommands();
        Player player = generateAvatar(mtree);
        GameMenu gmenu = new GameMenu();
        try {
            gmenu.fetchMenu(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Promts the user to start the game.
     * Continues to the configuration method and later to exploration -> GameMenu menu loop method
     * in Explore method of Gameplay class.
     *
     */
    public void initGame(Player player) throws Exception {
        Dialogue.promtBegin(player);
        String input = player.getRdtxt().nextLine();
        if (input.equals("y")) {
            Gameplay gameplay = new Gameplay();
            gameplay.Configure(player);
        } else {
            player.rdtxt.close();
            System.exit(0);
        }
        player.rdtxt.close();
    }

    public Player generateAvatar(MenuTree mTree) {
        Player player = new World().generatePlayer();
        player.setmTree(mTree);
        return player;
    }

    public void exit() {
        System.exit(0);
    }
}
