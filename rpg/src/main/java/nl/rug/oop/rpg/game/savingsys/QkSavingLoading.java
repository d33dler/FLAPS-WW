package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.File;

public class QkSavingLoading extends SavingSystem {
    @Override
    public void save(Player x) {
        createQsDir(x);
        super.save(x);
    }

    @Override
    public void setupLoad(Player x) {
        x.setSavefile("quicksaves/quicksave");
        super.setupLoad(x);
    }

    public void createQsDir(Player x) {
        File saveDir = new File("savedgames/quicksaves");
        if (!saveDir.mkdir()) {
            x.setSavefile("quicksaves/quicksave" + Integer.parseInt(String.valueOf(x.getQs() + 1)));
            x.setQs(x.getQs() + 1);
        }
    }
}
