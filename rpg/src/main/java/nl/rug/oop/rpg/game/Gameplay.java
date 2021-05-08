package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.game.configsys.ConfigImport;
import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.menu.builders.GameMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.*;

/**
 * Gameplay class holds the methods that configure
 * the world properties and initiate the game process
 */
public class Gameplay {
    /**
     * @param player object has the loaded config file, and it is used to set up the game parameters.
     * @throws Exception if config file is not loaded correctly/ extension issue/game world source code changes
     *                   which were not imprinted in the config setup
     */
    public void Configure(Player player) throws Exception {
        Dialogue.Comunication();
        player = new ConfigImport().setPlayerConfigs(player);
        Explore(player);
    }

    /**
     * @param player receives the MenuTree and the game menu class holding the menu loop method is
     *               initiated;
     */
    public void Explore(Player player) {
        player.setmTree(getMenuTree());
        GameMenu gmenu = new GameMenu();
        System.out.println("Greetings, " + player.getName() + "!\n \n");
        try {
            gmenu.fetchMenu(player);
        } catch (Exception e) {
            System.out.println("Error generating exploring menu. Exiting.");
        }
    }

    /**
     * @return Menu Tree structure from class MenuTree
     */
    public MenuTree getMenuTree() {
        MenuTree mTree = null;
        try {
            mTree = new GameMenuBuilder().buildGameMenu();
        } catch (NoSuchMethodException e) {
            System.out.println("Error generating menu tree");
        }
        return mTree;
    }
}





