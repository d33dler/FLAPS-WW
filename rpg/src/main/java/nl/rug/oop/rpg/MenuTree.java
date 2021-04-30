package nl.rug.oop.rpg;

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



}
