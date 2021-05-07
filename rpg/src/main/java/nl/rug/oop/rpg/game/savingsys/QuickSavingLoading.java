package nl.rug.oop.rpg.game.savingsys;
import nl.rug.oop.rpg.worldsystem.Player;
import java.io.File;

/**
 * Child class for overriding saving for quicksaves.
 */
public class QuickSavingLoading extends SavingSystem {
    @Override
    public void saveSave(Player player) {
        createQsDir(player);
        super.saveSave(player);
    }

    /**
     *
     * @param player setting up the boolean trigger which is checked in the game menu loop
     *               which is checked and if true - the loading process is initiated and player object
     */
    @Override
    public void setupLoad(Player player) {
        player.setSavefile("quicksaves/quicksave");
        super.setupLoad(player);
    }

    /**
     *
     * @param player creates the quicksave subdirectory if it doesn't exist yet and updates the default
     *               savefile name for quicksaves by the number of quicksaves already created, to preserve all
     *               quicksaves created.
     */
    public void createQsDir(Player player) {
        File saveDirparent = new File("savedgames");
        saveDirparent.mkdir();
        File saveDir = new File("savedgames/quicksaves");
        if (!saveDir.mkdir()) {
            player.setSavefile("quicksaves/quicksave" + Integer.parseInt(String.valueOf(player.getQs() + 1)));
            player.setQs(player.getQs() + 1);
        }
    }
}
