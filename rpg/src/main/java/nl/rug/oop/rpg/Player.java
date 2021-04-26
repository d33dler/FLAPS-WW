package nl.rug.oop.rpg;

public class Player extends Entity {

    public Player(InitEntity parameters) {
        this.name = parameters.name;
        this.location = parameters.location;
        this.health = parameters.health;
        this.damage = parameters.damage;
        this.inventory = parameters.inventory;
        this.money = parameters.money;
    }

    public String getName() {
        return this.name;
    }
}
