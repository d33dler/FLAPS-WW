package nl.rug.oop.rpg.itemsystem;
import nl.rug.oop.rpg.usersystem.AvatarInterface;
import nl.rug.oop.rpg.usersystem.PlayerInterfaceBuilder;
import java.io.Serializable;
import java.util.HashMap;

public class Inventory extends AvatarInterface implements Serializable {
    private static final long serialVersionUID = 3003L;

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

