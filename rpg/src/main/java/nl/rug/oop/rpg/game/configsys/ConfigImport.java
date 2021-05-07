package nl.rug.oop.rpg.game.configsys;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

import java.util.Properties;

public class ConfigImport extends Configurations{
    public Player setPlayerConfigs(Player player) throws Exception {
        Configurations config = new Configurations();
        player.setDefconfig(!player.isLoadconfig());
        player = config.loadConfig(player);
        World morph = new World();
        World map = morph.createMap(player);
        player.setMap(map);
        player = map.setupPlayer(player, map);
        Properties configs = player.getConfigs();
        player.setName(configs.getProperty("playerName"));
        player.setHealth(Integer.parseInt(configs.getProperty("playerHealth")));
        player.getTw().setSpeed(Integer.parseInt(configs.getProperty("twSpeed")));
        return player;
    }
}
