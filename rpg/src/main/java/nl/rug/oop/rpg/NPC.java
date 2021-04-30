package nl.rug.oop.rpg;

public abstract class NPC extends Entity implements Inspectable {

    public NPC(InitEntity parameters) {
        super(parameters);
    }

    @Override
    public void inspect(Room x) {
        NPC npc = x.getNpc();
        String name = npc.getName();
        System.out.println("You see a " + name + "");
        if (npc.health <= 0) {
            System.out.println("It's system components are not active\n ");
        }
    }
}

