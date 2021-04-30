package nl.rug.oop.rpg;

public abstract class Item implements Inspectable, Interactable {
    protected String name;
    protected int value;
    protected Typewriter tw = new Typewriter();

    @Override
    public void inspect(Room r) {
        if (r.loot instanceof Nothing) {
            tw.type("The room doesn't contain resources\n");
        } else {
            tw.type("You see a " + r.loot.name + " in a " + r.storage.getName() + "\n"); //random holder
        }

    }

    @Override
    public void interact(Player ava, int x) {
        Item item = ava.location.loot;
        if (!item.name.equals("nothing")) {
           tw.type("You picked a " + item.name + "\n Storing in inventory...\n");
            DepositInv(ava, item); //needs each weapon to have class indicated for matching by string
            removeItemRoom(ava.location);
            removeItemPlayer(ava);
        } else {
            tw.type("There is " + ava.location.loot.name + " here \n");
        }
    }

    public Item(InitItem parameters) {
        this.name = parameters.name; //
        this.value = parameters.value;
    }

    public void use(Player ava) {

    }

    public void Recycle(Player ava) {
        if (ava.hold instanceof Weapons) {
            tw.type("Recycling weapon for viable components & materials\n");
            tw.type("....\n");
            tw.type("Recycled: \n" + ava.hold.value + " units worth of materials.\n storing in inventory\n");
            Consumables item = new InitItem()
                    .name("Titanium(R)")
                    .hh(7)
                    .val(5)
                    .createCons();
            ava.inventory.cList.put("Titanium(R)", item);
        } else if (ava.hold instanceof Consumables) {
            tw.type("Recycling " + ava.hold.name + "\n");
        } else if (ava.hold instanceof Nothing) {
            tw.type("There is nothing here \n");
        }
        removeItemPlayer(ava);
        removeItemRoom(ava.location);
    }

    public void DepositInv(Player ava, Item item) {
        if (item instanceof Weapons) {
            ava.inventory.wList.put(item.name, (Weapons) item);     //future change - to hashmap
        } else if (item instanceof Consumables) {
            ava.inventory.cList.put(item.name, (Consumables) item);
        } else if (item instanceof Nothing) {
            tw.type("There is nothing to collect\n");
        }
    }

    public void removeItemPlayer(Player ava) {
        ava.hold = new InitItem().name("nothing").createNull();
    }

    public void removeItemRoom(Room r) {
        r.loot = new InitItem().name("nothing").createNull();
    }

    public void removeItem(Item x) {
        x = new InitItem().name("nothing").createNull();
    }

}

class Weapons extends Item {
    protected int dmg;

    public Weapons(InitItem parameters) {
        super(parameters);
        this.dmg = parameters.dmg;
    }

}

class Consumables extends Item {
    protected int health;

    public Consumables(InitItem parameters) {
        super(parameters);
        this.health = parameters.health;
    }
}

class Nothing extends Item {
    public Nothing(InitItem parameters) {
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