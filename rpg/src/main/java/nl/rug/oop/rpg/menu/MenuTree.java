package nl.rug.oop.rpg.menu;
import java.lang.reflect.*;
import java.util.EnumMap;
import java.util.HashMap;

public class MenuTree {
    protected MenuTree root;
    protected HashMap<String, Method> menunode;
    protected HashMap<String, MenuTree> submenus;
    protected EnumMap<?, String> optionlist;

    MenuTree(MenuNodeBuilder params) {
        this.root = params.root;
        this.menunode = params.menunode;
        this.submenus = params.submenus;
        this.optionlist = params.optionlist;
    }

    public MenuTree() {

    }

    public EnumMap<?, String> getOptionlist() {
        return optionlist;
    }

    public void setSubmenus(HashMap<String, MenuTree> submenus) {
        this.submenus = submenus;
    }

    public void setRoot(MenuTree root) {
        this.root = root;
    }

    public HashMap<String, Method> getMenunode() {
        return menunode;
    }

    public HashMap<String, MenuTree> getSubmenus() {
        return submenus;
    }

    public MenuTree getRoot() {
        return root;
    }
}
