package nl.rug.oop.rpg;

public abstract class Item implements Inspectable, Interactable {
    protected String name;
    protected int value;
    @Override
    public void inspect(Room r) {

    }
    @Override
    public void interact(Player ava, int x) {

    }

    public void use(Player ava) {

    }
    public void consume(Player ava) {

    }
}

class Weapons extends Item {
    protected int dmg;
    public Weapons(String name, int dmg, int value) {
        this.name = name;
        this.dmg = dmg;
        this.value = value;
    }
}

class Consumables extends Item {
    protected int health;
    public Consumables(String name, int health, int value) {
        this.name= name;
        this.health = health;
        this.value = value;
    }
}