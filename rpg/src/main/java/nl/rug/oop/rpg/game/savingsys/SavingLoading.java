package nl.rug.oop.rpg.game.savingsys;
import nl.rug.oop.rpg.game.configsys.FileSearch;
import nl.rug.oop.rpg.worldsystem.Player;
import java.io.File;

/**
 * Class SavingLoading used to save and load common saves.
 */
public class SavingLoading extends SavingSystem {
    /**
     *
     * @param player Overrides the parentclass method save and creates a saving directory
     *               if required. Requests a name for the file to be saved and uses the
     *               parent function to save the file.
     */
    @Override
    public void saveSave(Player player) {
        getSaveName(player);
        if (!player.getSavefile().equals("back")) {
            super.saveSave(player);
        }
    }

    /**
     *
     * @param player Overrides the parent class method setupLoad by creating a path for loading
     *               but notifying of a read error if a path needs creation prior to loading.
     *               Lists save files and request a name for the file to be extracted for loading.
     *               Uses the usual load method of the parent class.
     */
    @Override
    public void setupLoad(Player player) {
        FileSearch fs = new FileSearch();
        String abspath = "/home/radubereja/Desktop/Object-Oriented Programming/a0/2021_Team_060/rpg/savedgames/saves/";
        fs.listSaveFiles("savedgames/saves", ".ser", abspath);
        getSaveName(player);
        if (!player.getSavefile().equals("back")) {
            super.setupLoad(player);
        }
    }

    public void getSaveName(Player x) {
        x.getTw().type("Choose a save file & input name\n");
        x.setSavefile("saves/" + x.rdtxt.nextLine());
    }

}
