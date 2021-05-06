package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.SavingSystem;
import nl.rug.oop.rpg.worldsystem.Player;

import java.lang.reflect.Method;
import java.util.HashMap;

public class SaveMenuBuilder {
    SavingSystem saveInter  = new SavingSystem();
    GameMenu gmenu = new GameMenu();
    public MenuTree setMainCommands() throws NoSuchMethodException {
        HashMap<String, Method> options = new HashMap<>();
        options.put("a", saveInter.getClass().getMethod("quickSave", Player.class));
        options.put("b", saveInter.getClass().getMethod("quickLoad", Player.class));
        options.put("c", gmenu.getClass().getMethod("switchSaveMenu", Player.class));
        MenuTree savemenu = new MenuNodeBuilder().root(null).mNode(options).subM(new HashMap<>()).
                optL(SaveMenuOptions.getSaveOpt()).buildtree();
        savemenu.submenus.put("a", savemenu);
        return savemenu;
    }
}
