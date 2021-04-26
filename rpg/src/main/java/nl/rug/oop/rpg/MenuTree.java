package nl.rug.oop.rpg;

import java.awt.*;
import java.util.HashMap;

public class MenuTree {
    protected MenuTree root;
    protected HashMap<String, Commands> menunode;
    protected HashMap<String, MenuTree> submenus;
        MenuTree(MenuTree root, HashMap<String, Commands> menunode, HashMap<String, MenuTree> submenus) {

            this.root = root;
            this.menunode = menunode;
            this.submenus = submenus;
        }



}
