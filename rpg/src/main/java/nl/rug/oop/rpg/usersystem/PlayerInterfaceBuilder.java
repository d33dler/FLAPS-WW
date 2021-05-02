package nl.rug.oop.rpg.usersystem;

import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Consumables;
import nl.rug.oop.rpg.itemsystem.Weapons;

import java.util.HashMap;

public class PlayerInterfaceBuilder extends AvatarInterface {
    public PlayerInterfaceBuilder weaponInv(HashMap<String, Weapons> wList) {
        this.wList = wList;
        return this;
    }

    public PlayerInterfaceBuilder consumInv(HashMap<String, Consumables> cList) {
        this.cList = cList;
        return this;
    }

    public HashMap<String, Weapons> getwList() {
        return wList;
    }

    public HashMap<String, Consumables> getcList() {
        return cList;
    }

    public void setwList(HashMap<String, Weapons> wList) {
        this.wList = wList;
    }

    public void setcList(HashMap<String, Consumables> cList) {
        this.cList = cList;
    }

    public Inventory createInv() {
        return new Inventory(this);
    }
}
