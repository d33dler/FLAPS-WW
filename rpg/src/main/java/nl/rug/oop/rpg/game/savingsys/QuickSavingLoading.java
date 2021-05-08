package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

/**
 * Child class for overriding saving for quicksaves.
 */
public class QuickSavingLoading extends SavingSystem {
    @Override
    public void saveSave(Player player) {
        player.setSavefile("quicksaves/quicksave" + (player.getQs()+1));
        super.saveSave(player);
        player.setQs(player.getQs() + 1);
    }


    @Override
    public void setupLoad(Player player) {
        player.setSavefile("quicksaves/quicksave" + (player.getQs()));
        super.setupLoad(player);
    }
}
