package nl.rug.oop.rpg.worldsystem;
import java.util.concurrent.ThreadLocalRandom;
import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.menu.MenuBuilder;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Scanner;

public class Player extends Entity {
    protected boolean flee;
    protected Scanner rdtxt = new Scanner(System.in);
    protected WorldInteraction winter = new WorldInteraction();
    protected String sinput;
    protected int intin;
    protected int travel = 1;
    protected boolean hostile;
    protected Door used;
    protected MenuTree mTree;
    Typewriter tw = new Typewriter();
    protected int energycells;
    protected Door focus;
    protected boolean rabbit;

    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    {
        try {
            mTree = new MenuBuilder().buildMenuTree();
        } catch (NoSuchMethodException e) {
            tw.type("Error generating menu tree");
        }
    }

    public Player(EntityBuilder parameters) {
        super(parameters);
        this.rabbit = false;
    }
    public Door getUsed() {
        return used;
    }

    public void setUsed(Door used) {
        this.used = used;
    }

    public Door getFocus() {
        return focus;
    }

    public boolean isRabbit() {
        return rabbit;
    }

    public void setRabbit(boolean rabbit) {
        this.rabbit = rabbit;
    }

    public void setFocus(Door focus) {
        this.focus = focus;
    }

    public int getEnergycells() {
        return energycells;
    }

    public void setEnergycells(int energycells) {
        this.energycells = energycells;
    }

    public boolean isFlee() {
        return flee;
    }



    public boolean isHostile() {
        return hostile;
    }

    public int getTravelBuff() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public String getSinput() {
        return sinput;
    }

    public Typewriter getTw() {
        return tw;
    }

    public int getIntin() {
        return intin;
    }

    public void setIntin(int intin) {
        this.intin = intin;
    }

    public void setSinput(String sinput) {
        this.sinput = sinput;
    }

    public void setmTree(MenuTree mTree) {
        this.mTree = mTree;
    }

    public WorldInteraction getWinter() {
        return winter;
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
