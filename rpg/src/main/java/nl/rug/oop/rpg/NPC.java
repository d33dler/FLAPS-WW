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
        System.out.println("You see a " + name + "");
        if(npc.health<=0) {
            System.out.println("It's system components are not active\n'. ");
        }
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