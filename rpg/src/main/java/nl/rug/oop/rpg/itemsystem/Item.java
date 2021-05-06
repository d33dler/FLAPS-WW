package nl.rug.oop.rpg.itemsystem;

import java.io.Serializable;
import java.util.*;

import nl.rug.oop.rpg.*;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

public abstract class Item implements Inspectable, Interactable, Serializable {
    private static final long serialVersionUID = 856L;
    protected String name;
    protected int value;

    @Override
    public void inspect(Player x) {
        Room now = x.getLocation();
        if (now.getLoot() instanceof Nothing) {
            x.getTw().type("The room doesn't contain resources\n");
        } else {
            x.getTw().type("You see a " + now.getLoot().name + " in a " + now.getStorage().getName() + "\n"); //random holder
        }
    }

    @Override
    public void interact(Player x) {
        Item item = x.getLocation().getLoot();
        if (!item.name.equals("nothing")) {
            x.getTw().type("You picked an object: " + item.name + "\nStoring in inventory...\n");
            DepositInv(x, item); //needs each weapon to have class indicated for matching by string
            removeItemRoom(x.getLocation());
            removeItemPlayer(x);
        } else {
            x.getTw().type("Found  " + x.getLocation().getLoot().name + " here \n");
        }
    }

    public Item SelectItem(HashMap<String, ? extends Item> invlist, Scanner in) {

        Typewriter tw = new Typewriter();
        tw.type(" *(name) Select item   \n");
        Item item = invlist.get(in.nextLine());
        return item;
    }

    public Item(ItemBuilder parameters) {
        this.name = parameters.name; //
        this.value = parameters.value;
    }

    public Item() {
    }

    public void use(Player ava) {

    }

    public void Recycle(Player x) {
        if (x.getHold() instanceof Weapons) {
            x.getTw().type("Recycling weapon for viable components & materials\n");
            x.getTw().type("....\n");
            x.getTw().type("Recycled: \n" + x.getHold().value + " units worth of materials.\n storing in inventory\n");
            Consumables item = new ItemBuilder()
                    .name("Titanium(R)")
                    .hh(7)
                    .val(5)
                    .createCons();
            x.getInventory().getcList().put("Titanium(R)", item);
        } else if (x.getHold() instanceof Consumables) {
            x.getTw().type("Recycling " + x.getHold().name + "\n");
        } else if (x.getHold() instanceof Nothing) {
            x.getTw().type("There is nothing here \n");
        }
        removeItemPlayer(x);
        removeItemRoom(x.getLocation());
    }

    public void DepositInv(Player x, Item item) {
        if (item instanceof Weapons) {
            x.getInventory().getwList().put(item.name, (Weapons) item);     //future change - to hashmap
        } else if (item instanceof Consumables) {
            x.getInventory().getcList().put(item.name, (Consumables) item);
        } else if (item instanceof Nothing) {
            x.getTw().type("There is nothing to collect\n");
        }
    }

    public void removeItemPlayer(Player ava) {
        ava.setHold(new ItemBuilder().name("nothing").createNull());
    }

    public void removeItemRoom(Room r) {
        r.setLoot(new ItemBuilder().name("nothing").createNull());
    }

    public void removeItem(Item x) {
        x = new ItemBuilder().name("nothing").createNull();
    }

    public String getName() {
        return name;
    }
}

class Nothing extends Item implements Serializable {
    private static final long serialVersionUID = 432;
    public Nothing(ItemBuilder parameters) {
        super(parameters);
    }
}
/*
class Keycards extends Item {
    protected int health;
    public Keycards(String name, int health, int value) {
        this.name= name;
        this.health = health;
        this.value = value;
    }
}

 */