package nl.rug.oop.rpg;

public class Player extends Entity {
   protected boolean flee;

    public Player(InitEntity parameters) {
        super(parameters);
    }

    public String getName() {
        return this.name;
    }
}
