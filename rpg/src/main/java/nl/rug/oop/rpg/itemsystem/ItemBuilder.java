package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.worldsystem.Builders;

public class ItemBuilder extends Builders {
    protected String name;
    protected int dmg, health, value;

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder dmg(int dmg) {
        this.dmg = dmg;
        return this;
    }

    public ItemBuilder hh(int health) {
        this.health = health;
        return this;
    }

    public ItemBuilder val(int value) {
        this.value = value;
        return this;
    }

    public Weapons createWep() {
        return new Weapons(this);
    }

    public Consumables createCons() {
        return new Consumables(this);
    }

    public Nothing createNull() {
        return new Nothing(this);
    }
}
