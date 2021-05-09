package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

/**
 * Child class for overriding saving for quicksaves.
 */
public class QuickSavingLoading extends SavingSystem {
    @Override
    public void saveSave(Player player) {
        player.setSaveFile("quicksaves/quicksave" + (player.getQkSave()+1));
        super.saveSave(player);
        player.setQkSave(player.getQkSave() + 1);
    }


    @Override
    public void setupLoad(Player player) {
        player.setSaveFile("quicksaves/quicksave" + (player.getQkSave()));
        super.setupLoad(player);
    }
}
