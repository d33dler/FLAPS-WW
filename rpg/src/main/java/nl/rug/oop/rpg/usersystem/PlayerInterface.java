package nl.rug.oop.rpg.usersystem;

import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Consumables;
import nl.rug.oop.rpg.itemsystem.Weapons;
import nl.rug.oop.rpg.worldsystem.Builders;

import java.util.HashMap;

public class PlayerInterface extends Builders {
    protected HashMap<String, Weapons> wList;
    protected HashMap<String, Consumables> cList;

    public PlayerInterface weaponInv(HashMap<String, Weapons> wList) {
        this.wList = wList;
        return this;
    }

    public PlayerInterface consumInv(HashMap<String, Consumables> cList) {
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
