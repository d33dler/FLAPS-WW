package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;

import java.util.Scanner;

public class Player extends Entity {
   protected boolean flee;

    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    public boolean isFlee() {
        return flee;
    }

    public Player(EntityBuilder parameters) {
        super(parameters);
    }

    public String getName() {
        return this.name;
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }
}
