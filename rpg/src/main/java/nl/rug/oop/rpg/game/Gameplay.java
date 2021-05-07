package nl.rug.oop.rpg.game;
import nl.rug.oop.rpg.game.configsys.ConfigImport;
import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.menu.builders.GameMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.*;

public class Gameplay {

    public void Configure(Player player) throws Exception{
        Dialogue.Comunication();
        player = new ConfigImport().setPlayerConfigs(player);
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
        try {
            mTree = new GameMenuBuilder().buildGameMenu();
        } catch (NoSuchMethodException e) {
            System.out.println("Error generating menu tree");
        }
        return mTree;
    }
    public void questionTA(){

    }
}





