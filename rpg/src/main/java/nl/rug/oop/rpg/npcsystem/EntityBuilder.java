package nl.rug.oop.rpg.npcsystem;
import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

public class EntityBuilder extends Entity {
    public EntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public EntityBuilder hdm(int health, int damage, int money) {
        this.health = health;
        this.damage = damage;
        this.money = money;
        return this;
    }

    public EntityBuilder inv(Inventory inventory) {
        this.inventory = inventory;
        return this;
    }

    public EntityBuilder loc(Room loc) {
        this.location = loc;
        return this;
    }

    public EntityBuilder fl(Boolean flee) {
        this.flee = flee;
        return this;
    }

    public EntityBuilder ith(Item hold) {
        this.hold = hold;
        return this;
    }
    public EntityBuilder npcc(NPC cont) {
        this.npcContact = cont;
        return this;
    }
    public Player createProtagonist() {
        return new Player(this);
    }

    public Enemies createEnemy() {
        return new Enemies(this);
    }

    public Allies createFriend() {
        return new Allies(this);
    }
    public ExchangeBots createExBots() {
        return new ExchangeBots(this);
    }

}
