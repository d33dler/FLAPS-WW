package nl.rug.oop.rpg.worldsystem;

import java.io.Serializable;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.menu.builders.GameMenuBuilder;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Scanner;

public class Player extends Entity implements Serializable, Cloneable {
    private static final long serialVersionUID = 10L;
    public World map;
    protected String sinput;
    protected String savefile;
    protected int energycells;
    protected int intin;
    protected int travel;
    protected Door focus;
    protected Door used;
    protected boolean flee;
    protected boolean hostile;
    protected boolean rabbit;
    protected boolean loadfile;
    protected transient MenuTree mTree;
    protected transient MenuTree sMenu;
    public transient Scanner rdtxt;
    protected transient WorldInteraction winter;
    transient Typewriter tw;

    {
        try {
            mTree = new GameMenuBuilder().buildGameMenu();
        } catch (NoSuchMethodException e) {
            tw.type("Error generating menu tree");
        }
    }

    public Player(EntityBuilder parameters) {
        super(parameters);
        this.rabbit = false;
        this.loadfile = false;
        this.winter = new WorldInteraction();
        this.rdtxt = new Scanner(System.in);
        this.tw = new Typewriter();
        this.savefile = "default.ser";
        this.travel = 1;
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

    public String getSavefile() {
        return savefile;
    }

    public void setSavefile(String savefile) {
        this.savefile = savefile;
    }

    public void setTw(Typewriter tw) {
        this.tw = tw;
    }

    public void setWinter(WorldInteraction winter) {
        this.winter = winter;
    }

    public boolean isLoadfile() {
        return loadfile;
    }

    public void setLoad(boolean quickload) {
        this.loadfile = quickload;
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
