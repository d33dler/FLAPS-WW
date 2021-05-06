package nl.rug.oop.rpg.worldsystem;

import java.awt.*;
import java.io.Serializable;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.menu.MenuBuilder;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.menu.SaveMenuBuilder;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Scanner;

public class Player extends Entity implements Serializable, Cloneable {
    private static final long serialVersionUID = 10L;
    public World map;
    protected boolean flee;
    protected String sinput;
    protected int intin;
    protected int travel = 1;
    protected boolean hostile;
    protected Door used;
    protected transient MenuTree mTree;
    protected transient MenuTree sMenu;
    protected int energycells;
    protected Door focus;
    protected boolean rabbit;
    protected boolean savemenu;
    public transient Scanner rdtxt = new Scanner(System.in);
    protected transient WorldInteraction winter = new WorldInteraction();
    transient Typewriter tw = new Typewriter();

    {
        try {
            mTree = new MenuBuilder().buildGameMenu();
        } catch (NoSuchMethodException e) {
            tw.type("Error generating menu tree");
        }
    }

    public Player(EntityBuilder parameters) {
        super(parameters);
        this.rabbit = false;
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

    public void setTw(Typewriter tw) {
        this.tw = tw;
    }

    public void setWinter(WorldInteraction winter) {
        this.winter = winter;
    }

    public Player clone() throws CloneNotSupportedException {
        Player player = (Player) super.clone();
        player.used = (Door) used.clone();
        player.focus = (Door) focus.clone();
        player.map = (World) map.clone();
        return player;
    }

    public void setRdtxt(Scanner rdtxt) {
        this.rdtxt = rdtxt;
    }

    public void setFlee(boolean flee) {
        this.flee = flee;
    }

    public Door getUsed() {
        return used;
    }

    public void setUsed(Door used) {
        this.used = used;
    }

    public MenuTree getsMenu() {
        return sMenu;
    }

    public void setsMenu(MenuTree sMenu) {
        this.sMenu = sMenu;
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

    public World getMap() {
        return map;
    }

    public void setMap(World map) {
        this.map = map;
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

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public boolean isSavemenu() {
        return savemenu;
    }

    public void setSavemenu(boolean savemenu) {
        this.savemenu = savemenu;
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

    public boolean lifeCheck() {
        return false;
    }
}
