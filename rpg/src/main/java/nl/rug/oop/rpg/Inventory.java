package nl.rug.oop.rpg;

import java.util.HashMap;

public class Inventory extends AvatarInterface {

    public Inventory(PlayerInterface parameters) {
        super(parameters);
    }

    public Inventory() {
        super();
    }

    public Inventory generateInv() {
        HashMap<String, Weapons> wlist = new HashMap<>();
        HashMap<String, Consumables> clist = new HashMap<>();
        return new PlayerInterface().
                weaponInv(wlist).
                consumInv(clist).
                createInv();
    }
}
