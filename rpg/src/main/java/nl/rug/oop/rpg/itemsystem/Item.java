package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.*;
import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Item implements Inspectable, Interactable {
    protected String name;
    protected int value;
    protected Typewriter tw = new Typewriter();

    @Override
    public void inspect(Room r) {
        if (r.getLoot() instanceof Nothing) {
            tw.type("The room doesn't contain resources\n");
        } else {
            tw.type("You see a " + r.getLoot().name + " in a " + r.getStorage().getName() + "\n"); //random holder
        }

    }

    @Override
    public void interact(Player ava, int x) {
        Item item = ava.getLocation().getLoot();
        if (!item.name.equals("nothing")) {
           tw.type("You picked a " + item.name + "\n Storing in inventory...\n");
            DepositInv(ava, item); //needs each weapon to have class indicated for matching by string
            removeItemRoom(ava.getLocation());
            removeItemPlayer(ava);
        } else {
            tw.type("There is " + ava.getLocation().getLoot().name + " here \n");
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

    public void use(Player ava) {

    }

    public void Recycle(Player ava) {
        if (ava.getHold() instanceof Weapons) {
            tw.type("Recycling weapon for viable components & materials\n");
            tw.type("....\n");
            tw.type("Recycled: \n" + ava.getHold().value + " units worth of materials.\n storing in inventory\n");
            Consumables item = new ItemBuilder()
                    .name("Titanium(R)")
                    .hh(7)
                    .val(5)
                    .createCons();
            ava.getInventory().getcList().put("Titanium(R)", item);
        } else if (ava.getHold() instanceof Consumables) {
            tw.type("Recycling " + ava.getHold().name + "\n");
        } else if (ava.getHold() instanceof Nothing) {
            tw.type("There is nothing here \n");
        }
        removeItemPlayer(ava);
        removeItemRoom(ava.getLocation());
    }

    public void DepositInv(Player ava, Item item) {
        if (item instanceof Weapons) {
            ava.getInventory().getwList().put(item.name, (Weapons) item);     //future change - to hashmap
        } else if (item instanceof Consumables) {
            ava.getInventory().getcList().put(item.name, (Consumables) item);
        } else if (item instanceof Nothing) {
            tw.type("There is nothing to collect\n");
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

    public static class ItemCheck implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            GameCommands option;
            String input;
            Room r = x.getLocation();
            Item item = r.getLoot();
            EnumMap<ItemOptions, String> iteminter = ItemOptions.getItem();
            do {
                iteminter.values().forEach(System.out::println);
                input = in.nextLine();
                option = menu.get(input);
                option.exec(x, in, menu, menuTr);
            } while (!input.equals("back") && !item.name.equals("nothing"));
        }
    }


}

class Nothing extends Item {
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