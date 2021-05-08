package nl.rug.oop.rpg.menu.builders;
import nl.rug.oop.rpg.game.configsys.Configurations;
import nl.rug.oop.rpg.game.RPG;
import nl.rug.oop.rpg.menu.options.MainMenuOptions;
import nl.rug.oop.rpg.worldsystem.Player;
import java.lang.reflect.Method;
import java.util.HashMap;

public class MainMenuBuilder {
Configurations config = new Configurations();
RPG mm = new RPG();
    public MenuTree setMainCommands() throws NoSuchMethodException {
        HashMap<String, Method> options = new HashMap<>();
        options.put("a", mm.getClass().getMethod("initGame", Player.class));
        options.put("b", config.getClass().getMethod("loadConfig", Player.class));
        options.put("c", config.getClass().getMethod("saveConfig", Player.class));
        options.put("d", config.getClass().getMethod("loadConfig", Player.class));
        options.put("x", mm.getClass().getMethod("exit"));
        return new MenuNodeBuilder().root(null).mNode(options).subM(new HashMap<>()).
                optL(MainMenuOptions.getSaveOpt()).buildtree();
    }
}
