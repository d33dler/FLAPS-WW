package nl.rug.oop.rpg.menu;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;

public class mNodeBuilder extends MenuTree {
    public mNodeBuilder root(MenuTree root){
        this.root = root;
        return this;
    }
    public mNodeBuilder mNode(HashMap<String, Method> menunode) {
        this.menunode = menunode;
        return this;
    }
    public mNodeBuilder subM(HashMap<String,MenuTree> submenus) {
        this.submenus = submenus;
        return this;
    }
    public mNodeBuilder optL(EnumMap<?,String> optionlist) {
        this.optionlist = optionlist;
        return this;
    }
    public MenuTree buildtree() {
        return new MenuTree(this);
    }
}
