package nl.rug.oop.rpg.game;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

public class SaveData {
    protected World map;
    Player player;

    public SaveData(SaveDataBuilder objects) {
        this.map = objects.map;
        this.player = objects.player;
    }

    public SaveData() {

    }
}
