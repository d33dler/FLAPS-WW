package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.menu.MenuBuilder;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;

import java.util.Scanner;

public class Player extends Entity {
    protected boolean flee;
    protected MenuTree mTree = new MenuBuilder().buildMenuTree();
    protected Scanner rdtxt = new Scanner(System.in);
    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    public boolean isFlee() {
        return flee;
    }

    public Player(EntityBuilder parameters) throws NoSuchMethodException {
        super(parameters);
    }

    public NPC getNpccontact() {
        return npccontact;
    }

    public MenuTree getmTree() {
        return mTree;
    }

    public void setNpccontact(NPC npccontact) {
        this.npccontact = npccontact;
    }

    public String getName() {
        return this.name;
    }

    public Scanner getRdtxt() {
        return rdtxt;
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

    public void attackPlayer() {

    }

    public void defendPlayer() {

    }

    public void fleePlayer() {

    }

    public void tradePlayer() {

    }

    public boolean lifeCheck() {
        return false;
    }

}
