package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;

public class Enemies extends NPC implements Interactable, Inspectable,Attackable {
    private static final long serialVersionUID = 1203L;
    public Enemies(EntityBuilder parameters) {
        super(parameters);
    }

    /**
     *  @param a
     *
     */

    @Override
    public void interact(Player a) {
    }

    @Override
    public void receiveattack(Player x) {

    }
}
