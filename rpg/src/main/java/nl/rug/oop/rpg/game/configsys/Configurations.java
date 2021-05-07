package nl.rug.oop.rpg.game.configsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.*;
import java.util.Properties;


public class Configurations implements Serializable {
    private final static long serialVersionUID = 1563L;

    /**
     * @param player Player class instance holds all the necessary configuration data fields
     *               which are to be saved
     */
    public void saveConfig(Player player) {
        File configDir = new File("config");
        boolean info = !configDir.mkdir();
        player.setDefconfig(true);
        Properties config = getConfig(player);
        try {
            FileOutputStream fstream = new FileOutputStream("config/" + player.getConfigfile() + ".properties");
            ObjectOutputStream configs = new ObjectOutputStream(fstream);
            config.store(fstream, "RpgConfigFile");
            configs.flush();
            configs.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("Error creating default config file");
        }
        if (info) {
            System.out.println("Default config file & path was created successfully.");
        }
    }

    /**
     * @param player holds the field value for the loaded configuration data properties
     * @return player with new config data.
     */
    public Player loadConfig(Player player) {
        File configDir = new File("config");
        if (configDir.mkdir()) {
            saveConfig(player);
        }
        Properties config = getConfig(player);
        try {
            FileInputStream fstream = new FileInputStream("config/" + player.getConfigfile() + ".properties");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            config.load(fstream);
            objectStream.close();
            fstream.close();
            System.out.println("Config file was loaded successfully");
            player.setConfigs(config);
            player.setLoadconfig(true);
        } catch (Exception e) {
            System.out.println("Error loading config file");
        }
        return player;
    }

    /**
     * Default configuration data;
     */
    public Properties setupDefaultConfig(Properties rpgProp) {
        rpgProp.setProperty("playerName", "tassist");
        rpgProp.setProperty("playerHealth", "100");
        rpgProp.setProperty("twSpeed", "50");
        rpgProp.setProperty("roomNr", "50");
        rpgProp.setProperty("door%", "70");
        rpgProp.setProperty("crypt%", "20");
        rpgProp.setProperty("copy%", "10");
        rpgProp.setProperty("ultra%", "20");
        return rpgProp;
    }

    /**
     *
     * @param player
     * @return
     */
    public Properties getConfig(Player player) {
        Properties rpgProp = new Properties();
        if (player.isDefconfig()) {
            player.setConfigfile("default");
            rpgProp = setupDefaultConfig(rpgProp);
        } else if (!player.isLoadconfig()) {
            listConfigFiles();
            getConfigName(player);
        }
        return rpgProp;
    }

    public void getConfigName(Player x) {
        x.getTw().type("Choose a config file & input name\n");
        x.setConfigfile(x.rdtxt.nextLine());
    }

    public void listConfigFiles() {
        FileSearch fs = new FileSearch();
        fs.listSaveFiles("config/",
                ".properties",
                "/home/radubereja/Desktop/Object-Oriented Programming/a0/2021_Team_060/rpg/config/");
    }

}
