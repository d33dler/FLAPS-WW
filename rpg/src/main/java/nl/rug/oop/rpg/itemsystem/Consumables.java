package nl.rug.oop.rpg.itemsystem;

public class Consumables extends Item {
    protected int health;
    private static final long serialVersionUID = 124L;
    public Consumables(ItemBuilder parameters) {
        super(parameters);
        this.health = parameters.health;
    }
}
