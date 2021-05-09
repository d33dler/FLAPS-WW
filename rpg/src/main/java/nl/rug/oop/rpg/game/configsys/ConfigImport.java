package nl.rug.oop.rpg.game.configsys;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;
import java.util.Properties;

/**
 * Used to import all configuration data and set up the world before the initialization of the game process.
 */

public class ConfigImport extends Configurations {
    public Player setPlayerConfigs(Player player) throws Exception {
        Configurations config = new Configurations();
        player.setDefConfig(!player.isLoadConfig());
        player = config.loadConfig(player);
        World morph = new World();
        World map = morph.createMap(player);
        player.setMap(map);
        player = map.setupPlayer(player, map);
        Properties configs = player.getConfigs();
        player.setName(configs.getProperty("playerName"));
        player.setHealth(Integer.parseInt(configs.getProperty("playerHealth")));
        player.gettW().setSpeed(Integer.parseInt(configs.getProperty("twSpeed")));
        return player;
    }
}
