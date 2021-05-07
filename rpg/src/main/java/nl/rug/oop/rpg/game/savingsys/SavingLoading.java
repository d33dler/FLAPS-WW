package nl.rug.oop.rpg.game.savingsys;
import nl.rug.oop.rpg.game.configsys.FileSearch;
import nl.rug.oop.rpg.worldsystem.Player;
import java.io.File;


public class SavingLoading extends SavingSystem {
    @Override
    public void save(Player x) {
        createSaveDir(x);
        getSaveName(x);
        if (!x.getSavefile().equals("back")) {
            super.save(x);
        }
    }

    @Override
    public void setupLoad(Player x) {
        createSaveDir(x);
        FileSearch fs = new FileSearch();
        String abspath = "/home/radubereja/Desktop/Object-Oriented Programming/a0/2021_Team_060/rpg/savedgames/saves/";
        fs.listSaveFiles("savedgames/saves", ".ser", abspath);
        getSaveName(x);
        if (!x.getSavefile().equals("back")) {
            super.setupLoad(x);
        }
    }

    public void getSaveName(Player x) {
        x.getTw().type("Choose a save file & input name\n");
        x.setSavefile("saves/" + x.rdtxt.nextLine());
    }

    public void createSaveDir(Player x) {
        File saveDir = new File("savedgames/saves");
        saveDir.mkdir();
    }
}
