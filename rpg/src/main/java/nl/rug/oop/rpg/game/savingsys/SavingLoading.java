package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.IOException;

public class SavingLoading extends SavingSystem {
    @Override
    public void save(Player x) {
        getSaveName(x);
        super.save(x);
    }

    @Override
    public void setupLoad(Player x) {
        listSaveFiles();
        getSaveName(x);
        if (!x.getSavefile().equals("back")) {
            super.setupLoad(x);
        }
    }

    public void getSaveName(Player x) {
        x.getTw().type("Choose a save file & input name\n");
        System.out.println("(back) Back");
        x.setSavefile(x.rdtxt.nextLine());
    }
}
