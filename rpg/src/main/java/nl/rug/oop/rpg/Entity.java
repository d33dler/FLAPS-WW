package nl.rug.oop.rpg;

import java.util.List;

public abstract class Entity {
    protected String name;
    protected int health,damage,money;
    protected List<Item> inventory;
    protected Room location;

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
    public List<Item> getInventory() {
        return inventory;
    }
    public void setInventory(List<Item> inventory) {
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
