package nl.rug.oop.rpg.worldsystem;

import java.io.Serializable;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.menu.builders.MenuTree;
import nl.rug.oop.rpg.npcsystem.*;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.util.Properties;
import java.util.Scanner;

/**
 * Player class holding all relevant fields for config files, save files, the world map etc.
 */

public class Player extends NPC implements Serializable, Attackable {
    private static final long serialVersionUID = 10L;
    public World map;
    protected String strIn, saveFile, configFile, intent;
    protected int energyCells, intIn, travel, qkSave;
    protected Door doorFocus, used;
    protected NPC npcFocus;
    protected boolean flee, hostile, rabbit, forcedCombat;
    transient protected boolean loadFile, loadConfig, defConfig;
    protected transient MenuTree mTree;
    public transient Scanner readTxt;
    protected transient WorldInteraction wInter;
    protected transient Properties rpgProper;
    transient Typewriter tW;

    /**
     *
     * @param parameters creating the player object using the builder pattern implementing constructors
     *                   from the Entity Builder class
     */
    public Player(EntityBuilder parameters) {
        super(parameters);
        this.rabbit = false;
        this.loadFile = false;
        this.defConfig = false;
        this.loadConfig = false;
        this.wInter = new WorldInteraction();
        this.readTxt = new Scanner(System.in);
        this.tW = new Typewriter();
        this.saveFile = "default";
        this.strIn = "default";
        this.configFile = "default";
        this.intent = "default";
        this.travel = 1;
        this.qkSave = 0;
        this.forcedCombat = false;
    }

    /**
     *
     * @param attacker Object that decreases the integer health field value of the victim object.
     * @param victim   Object which has its health field value updated.
     */
    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        super.receiveAttack(attacker, victim);
        Dialogue.notifyDamagePlayer((Player) victim);
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

    public String getSaveFile() {
        return saveFile;
    }

    public boolean isLoadConfig() {
        return loadConfig;
    }

    public void setLoadConfig(boolean loadConfig) {
        this.loadConfig = loadConfig;
    }

    public NPC getNpcFocus() {
        return npcFocus;
    }

    public void setNpcFocus(NPC npcFocus) {
        this.npcFocus = npcFocus;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    public Properties getConfigs() {
        return rpgProper;
    }

    public void setConfigs(Properties rpgprop) {
        this.rpgProper = rpgprop;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public void settW(Typewriter tW) {
        this.tW = tW;
    }

    public boolean isForcedCombat() {
        return forcedCombat;
    }

    public void setForcedCombat(boolean forcedCombat) {
        this.forcedCombat = forcedCombat;
    }

    public void setwInter(WorldInteraction wInter) {
        this.wInter = wInter;
    }

    public boolean isLoadFile() {
        return loadFile;
    }

    public void setLoad(boolean quickload) {
        this.loadFile = quickload;
    }

    public boolean isDefConfig() {
        return defConfig;
    }

    public void setDefConfig(boolean defConfig) {
        this.defConfig = defConfig;
    }

    public int getQkSave() {
        return qkSave;
    }

    public void setQkSave(int qkSave) {
        this.qkSave = qkSave;
    }

    public void setReadTxt(Scanner readTxt) {
        this.readTxt = readTxt;
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

    public int getEnergyCells() {
        return energyCells;
    }

    public void setEnergyCells(int energyCells) {
        this.energyCells = energyCells;
    }

    public boolean isFlee() {
        return flee;
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

    public String getStrIn() {
        return strIn;
    }

    public Typewriter gettW() {
        return tW;
    }

    public void setHostile(boolean hostile) {
        this.hostile = hostile;
    }

    public int getIntIn() {
        return intIn;
    }

    public void setIntIn(int intIn) {
        this.intIn = intIn;
    }

    public void setStrIn(String strIn) {
        this.strIn = strIn;
    }

    public void setmTree(MenuTree mTree) {
        this.mTree = mTree;
    }

    public WorldInteraction getwInter() {
        return wInter;
    }

    public NPC getNpccontact() {
        return npcContact;
    }

    public MenuTree getmTree() {
        return mTree;
    }

    public void setNpccontact(NPC npccontact) {
        this.npcContact = npccontact;
    }

    public String getName() {
        return this.name;
    }

    public Scanner getReadTxt() {
        return readTxt;
    }

    public boolean lifeCheck(Player player) {
        return new NpcInteraction().lifeCheck(player);
    }


}
