package nl.rug.oop.rpg.itemsystem;

public class Weapons extends Item {
    protected int dmg;

    public Weapons(ItemBuilder parameters) {
        super(parameters);
        this.dmg = parameters.dmg;
    }

}
