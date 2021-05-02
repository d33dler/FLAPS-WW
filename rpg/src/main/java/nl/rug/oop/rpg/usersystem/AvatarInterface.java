package nl.rug.oop.rpg.usersystem;

import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Consumables;
import nl.rug.oop.rpg.itemsystem.Weapons;

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

    public HashMap<String, Weapons> getwList() {
        return wList;
    }

    public void setwList(HashMap<String, Weapons> wList) {
        this.wList = wList;
    }

    public HashMap<String, Consumables> getcList() {
        return cList;
    }

    public void setcList(HashMap<String, Consumables> cList) {
        this.cList = cList;
    }
}
