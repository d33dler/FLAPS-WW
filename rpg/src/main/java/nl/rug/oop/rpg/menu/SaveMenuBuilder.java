package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.SavingSystem;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SaveMenuBuilder {
    SavingSystem saveInter = new SavingSystem();
    WorldInteraction winter = new WorldInteraction();

    public MenuTree setMainCommands() throws NoSuchMethodException {
        HashMap<String, Method> options = new HashMap<>();
        options.put("a", saveInter.getClass().getMethod("quickSave", Player.class));
        options.put("b", saveInter.getClass().getMethod("quickLoad", Player.class));
        options.put("c", winter.getClass().getMethod("goBack", Player.class));
        MenuTree savemenu = new MenuNodeBuilder().root(null).mNode(options).subM(new HashMap<>()).
                optL(SaveMenuOptions.getSaveOpt()).buildtree();
       // savemenu.submenus.put("a", savemenu);
        return savemenu;
    }
}
