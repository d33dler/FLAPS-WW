package nl.rug.oop.rpg.itemsystem;

public class Consumables extends Item {
    protected int health;

    public Consumables(ItemBuilder parameters) {
        super(parameters);
        this.health = parameters.health;
    }
}
