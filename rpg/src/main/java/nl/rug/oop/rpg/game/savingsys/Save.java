package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface Save {
    void saveSave(Player x);
    Player loadSave(Player x) throws IOException, ClassNotFoundException;
}
