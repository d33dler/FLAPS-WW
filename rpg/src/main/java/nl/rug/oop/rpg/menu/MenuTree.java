package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.GameCommands;

import java.util.HashMap;

public class MenuTree {
    protected MenuTree root;
    protected HashMap<String, GameCommands> menunode;
    protected HashMap<String, MenuTree> submenus;
        MenuTree(MenuTree root, HashMap<String, GameCommands> menunode, HashMap<String, MenuTree> submenus) {
            this.root = root;
            this.menunode = menunode;
            this.submenus = submenus;
        }

    public HashMap<String, GameCommands> getMenunode() {
        return menunode;
    }

    public HashMap<String, MenuTree> getSubmenus() {
        return submenus;
    }

    public MenuTree getRoot() {
        return root;
    }
}
