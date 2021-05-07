package nl.rug.oop.rpg.worldsystem;

import java.io.Serializable;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.npcsystem.*;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Properties;
import java.util.Scanner;


public class Player extends NPC implements Serializable, Attackable {
    private static final long serialVersionUID = 10L;
    public World map;
    protected String sinput, savefile, configfile, intent;
    protected int energycells, intin, travel, qs;
    protected Door doorFocus, used;
    protected NPC npcFocus;
    protected boolean flee, hostile, rabbit, forcedcomb;
    transient protected boolean narrative, loadfile, loadconfig, defconfig;
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
        this.intent = "default";
        this.travel = 1;
        this.qs = 0;
        this.forcedcomb = false;
    }

    public void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

    public void cappingTravelBuff(Player player) {
        if (player.getTravelBuff() > 5) {
            player.setTravelBuff(5);
        }
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

    public NPC getNpcFocus() {
        return npcFocus;
    }

    public void setNpcFocus(NPC npcFocus) {
        this.npcFocus = npcFocus;
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

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void setTw(Typewriter tw) {
        this.tw = tw;
    }

    public boolean isForcedcomb() {
        return forcedcomb;
    }

    public void setForcedcomb(boolean forcedcomb) {
        this.forcedcomb = forcedcomb;
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


    public Door getDoorFocus() {
        return doorFocus;
    }

    public boolean isRabbit() {
        return rabbit;
    }

    public void setRabbit(boolean rabbit) {
        this.rabbit = rabbit;
    }

    public void setDoorFocus(Door doorFocus) {
        this.doorFocus = doorFocus;
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

    public void setTravelBuff(int travel) {
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

    public boolean lifeCheck(Player player) {
        return new NpcInteraction().lifeCheck(player);
    }

    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        super.receiveAttack(attacker, victim);
        Dialogue.notifyDamagePlayer((Player) victim);
    }
}
