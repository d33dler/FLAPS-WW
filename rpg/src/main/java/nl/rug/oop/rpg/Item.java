package nl.rug.oop.rpg;

public abstract class Item implements Inspectable, Interactable {
    protected String name;

}

class Weapons extends Item {

    @Override
    public void inspect(Room r) {

    }

    @Override
    public void interact(Player ava) {

    }
}

class Consumables extends Item {

    @Override
    public void inspect(Room r) {

    }

    @Override
    public void interact(Player ava) {

    }
}