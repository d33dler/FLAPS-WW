package nl.rug.oop.rpg;

import java.util.List;

public abstract class Entity {
    protected String name;
    protected int health, damage, money;
    protected Inventory inventory;
    protected Room location;
    protected Item hold;

    public Entity(InitEntity parameters) {
        this.name = parameters.name;
        this.location = parameters.location;
        this.health = parameters.health;
        this.damage = parameters.damage;
        this.inventory = parameters.inventory;
        this.money = parameters.money;
        this.hold = parameters.hold;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getMoney() {
        return money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public Room getLocation() {
        return location;
    }

    public void setLocation(Room location) {
        this.location = location;
    }

}
