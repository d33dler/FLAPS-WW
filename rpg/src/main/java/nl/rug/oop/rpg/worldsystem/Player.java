package nl.rug.oop.rpg.worldsystem;

import java.io.Serializable;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.game.configsys.Configurations;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.EntityBuilder;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Properties;
import java.util.Scanner;


public class Player extends Entity implements Serializable, Cloneable {
    private static final long serialVersionUID = 10L;
    public World map;
    protected String sinput, savefile,configfile;
    protected int energycells, intin, travel, qs;
    protected Door focus, used;
    protected boolean flee, hostile, rabbit;
    transient protected boolean  loadfile, loadconfig, defconfig;
    protected transient MenuTree mTree;
    public transient Scanner rdtxt;
    protected transient WorldInteraction winter;
    protected transient Properties rpgprop;
    transient Typewriter tw;

    public Player(EntityBuilder parameters) {
        super(parameters);
        this.rabbit = false;
        this.loadfile = false;
        this.defconfig = false;
        this.loadconfig = false;
        this.winter = new WorldInteraction();
        this.rdtxt = new Scanner(System.in);
        this.tw = new Typewriter();
        this.savefile = "default";
        this.sinput = "default";
        this.configfile = "default";
        this.travel = 1;
        this.qs = 0;
        // this.tw.setSpeed(50);
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

    public String getSavefile() {
        return savefile;
    }

    public boolean isLoadconfig() {
        return loadconfig;
    }

    public void setLoadconfig(boolean loadconfig) {
        this.loadconfig = loadconfig;
    }

    public String getConfigfile() {
        return configfile;
    }

    public void setConfigfile(String configfile) {
        this.configfile = configfile;
    }

    public Properties getConfigs() {
        return rpgprop;
    }

    public void setConfigs(Properties rpgprop) {
        this.rpgprop = rpgprop;
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

    public boolean isDefconfig() {
        return defconfig;
    }

    public void setDefconfig(boolean defconfig) {
        this.defconfig = defconfig;
    }

    public int getQs() {
        return qs;
    }

    public void setQs(int qs) {
        this.qs = qs;
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
