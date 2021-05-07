package nl.rug.oop.rpg.menu;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Gameplay;
import nl.rug.oop.rpg.menu.builders.MainMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

public class RPG {

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
