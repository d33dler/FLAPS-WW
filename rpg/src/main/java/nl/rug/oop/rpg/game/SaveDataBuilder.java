package nl.rug.oop.rpg.game;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

public class SaveDataBuilder extends SaveData {
    public SaveDataBuilder map(World map) {
        this.map = map;
        return this;
    }

    public SaveDataBuilder player(Player player) {
        this.player = player;
        return this;
    }

    public SaveData loadSave() {
        return new SaveData(this);
    }
}

