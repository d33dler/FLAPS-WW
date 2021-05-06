package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.IOException;

public class QkSavingLoading extends SavingSystem{
    @Override
    public void save(Player x) {
        x.setSavefile("quicksave.ser");
        super.save(x);
    }

    @Override
    public void setupLoad(Player x) {
        super.setupLoad(x);
    }
}
