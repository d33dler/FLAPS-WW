package nl.rug.oop.rpg.itemsystem;
import java.lang.reflect.*;
import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.usersystem.AvatarInterface;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.usersystem.PlayerInterfaceBuilder;
import nl.rug.oop.rpg.worldsystem.Room;


import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class Inventory extends AvatarInterface {

    public Inventory(PlayerInterfaceBuilder parameters) {
        super(parameters);
    }

    public Inventory() {
        super();
    }

    public Inventory generateInv() {
        HashMap<String, Weapons> wlist = new HashMap<>();
        HashMap<String, Consumables> clist = new HashMap<>();
        return new PlayerInterfaceBuilder().
                weaponInv(wlist).
                consumInv(clist).
                createInv();
    }
}

