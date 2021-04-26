package nl.rug.oop.rpg;

public abstract class NPC extends Entity implements Inspectable {

    public NPC(InitEntity parameters) {
        this.name = parameters.name;
        this.location = parameters.location;
        this.health = parameters.health;
        this.damage = parameters.damage;
        this.inventory = parameters.inventory;
        this.money = parameters.money;
    }

    @Override
    public void inspect(Room x) {
        NPC npc = x.getNpc();
        String name = npc.getName();
        System.out.println("You see a " + name + "\n");
    }

}

class Enemies extends NPC {

    public Enemies(InitEntity parameters) {
        super(parameters);
    }
}
class Allies extends NPC {

    public Allies(InitEntity parameters) {
        super(parameters);
    }
}