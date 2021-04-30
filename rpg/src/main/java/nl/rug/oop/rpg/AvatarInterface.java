package nl.rug.oop.rpg;

import java.util.*;

public abstract class AvatarInterface {
    protected HashMap<String, HashMap<String, ? extends Inventory>> maininventory;
    protected HashMap<String, Weapons> wList;
    protected HashMap<String, Consumables> cList;

    public AvatarInterface(PlayerInterface parameters) {
        this.wList = parameters.wList;
        this.cList = parameters.cList;
    }

    public AvatarInterface() {
    }
}
