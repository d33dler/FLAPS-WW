package nl.rug.oop.rpg.menu.builders;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;

public class MenuNodeBuilder extends MenuTree {
    public MenuNodeBuilder root(MenuTree root){
        this.root = root;
        return this;
    }
    public MenuNodeBuilder mNode(HashMap<String, Method> menunode) {
        this.menunode = menunode;
        return this;
    }
    public MenuNodeBuilder subM(HashMap<String,MenuTree> submenus) {
        this.submenus = submenus;
        return this;
    }
    public MenuNodeBuilder optL(EnumMap<?,String> optionlist) {
        this.optionlist = optionlist;
        return this;
    }
    public MenuTree buildtree() {
        return new MenuTree(this);
    }
}
