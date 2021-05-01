package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.worldsystem.Builders;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

public class InitEntity extends Builders {
    protected String name;
    protected int health, damage, money;
    protected Inventory inventory;
    protected Room location;
    protected Item hold;
    protected boolean flee;

    public InitEntity name(String name) {
        this.name = name;
        return this;
    }

    public InitEntity hdm(int health, int damage, int money) {
        this.health = health;
        this.damage = damage;
        this.money = money;
        return this;
    }

    public InitEntity inv(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public InitEntity loc(Room loc) {
        this.location = loc;
        return this;
    }

    public InitEntity fl(Boolean flee) {
        this.flee = flee;
        return this;
    }

    public InitEntity ith(Item hold) {
        this.hold = hold;
        return this;
    }

    public Player protagonist() {
        return new Player(this);
    }

    public Enemies createn() {
        return new Enemies(this);
    }

    public Allies createfr() {
        return new Allies(this);
    }
}
