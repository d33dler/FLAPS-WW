package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.InitEntity;

public class Player extends Entity {
   protected boolean flee;

    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    public boolean isFlee() {
        return flee;
    }

    public Player(InitEntity parameters) {
        super(parameters);
    }

    public String getName() {
        return this.name;
    }
}
