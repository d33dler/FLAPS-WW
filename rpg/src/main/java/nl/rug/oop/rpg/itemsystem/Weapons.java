package nl.rug.oop.rpg.itemsystem;

public class Weapons extends Item {
    protected int dmg;
    private static final long serialVersionUID = 123L;
    public Weapons(ItemBuilder parameters) {
        super(parameters);
        this.dmg = parameters.dmg;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (Weapons) super.clone();
    }
}
