package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.game.configsys.Configurations;
import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.menu.builders.GameMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.*;

import java.util.Properties;


public class Gameplay {

    public void Configure(Player player) {
        //Dialogue intro = new Dialogue();
        //intro.Comunication();
        Configurations config = new Configurations();
        player.setDefconfig(!player.isLoadconfig());
        player = config.loadConfig(player);
        World morph = new World();
        World map = morph.createMap(player);
        player.setMap(map);
        player = map.setupPlayer(player, map);
        Properties configs = player.getConfigs();
        player.setName(configs.getProperty("playerName"));
        player.setHealth(Integer.parseInt(configs.getProperty("playerHealth")));
        player.getTw().setSpeed(Integer.parseInt(configs.getProperty("twSpeed")));
        Explore(player);
    }

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


    public MenuTree getMenuTree() {
        MenuTree mTree = null;
        {
            try {
                mTree = new GameMenuBuilder().buildGameMenu();
            } catch (NoSuchMethodException e) {
                System.out.println("Error generating menu tree");
            }
            return mTree;
        }
    }
}





