package nl.rug.oop.rpg.itemsystem;
import nl.rug.oop.rpg.usersystem.AvatarInterface;
import nl.rug.oop.rpg.usersystem.PlayerInterfaceBuilder;
import java.util.HashMap;


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

