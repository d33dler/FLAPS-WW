package nl.rug.oop.rpg.game.configsys;

import nl.rug.oop.rpg.game.FileSearch;
import nl.rug.oop.rpg.worldsystem.Player;

import java.io.*;
import java.util.Properties;


public class Configurations implements Serializable {
    private final static long serialVersionUID = 1563L;

    public void saveConfig(Player x) {
        File configDir = new File("config");
        configDir.mkdir();
        x.setDefconfig(true);
        Properties config = getConfig(x);
        try {
            FileOutputStream fstream = new FileOutputStream("config/" + x.getConfigfile() + ".properties");
            ObjectOutputStream configs = new ObjectOutputStream(fstream);
            config.store(fstream, "RpgConfigFile");
            configs.flush();
            configs.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("Error creating default config file");
        }
        System.out.println("Default config file was created successfully.");
    }

    public Player loadConfig(Player x) {
        File configDir = new File("config");
        if (configDir.mkdir()) {
            saveConfig(x);
        }
        Properties config = getConfig(x);
        try {
            FileInputStream fstream = new FileInputStream("config/" + x.getConfigfile() + ".properties");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            config.load(fstream);
            objectStream.close();
            fstream.close();
            System.out.println("Config file was loaded successfully");
            x.setConfigs(config);
            x.setLoadconfig(true);
        } catch (Exception e) {
            System.out.println("Error loading config file");
        }
        return x;
    }

    public Properties setupDefaultConfig(Properties rpgProp) {
        rpgProp.setProperty("playerName", "tassist");
        rpgProp.setProperty("playerHealth", "100");
        rpgProp.setProperty("twSpeed", "50");
        rpgProp.setProperty("roomNr", "50");
        rpgProp.setProperty("door%", "60");
        rpgProp.setProperty("crypt%", "20");
        rpgProp.setProperty("copy%", "10");
        rpgProp.setProperty("ultra%", "10");
        return rpgProp;
    }

    public Properties getConfig(Player x) {
        Properties rpgProp = new Properties();
        if (x.isDefconfig()) {
            x.setConfigfile("default");
            rpgProp = setupDefaultConfig(rpgProp);
            x.getTw().type(x.isDefconfig()+ "Loading default config file\n");
        } else if (!x.isLoadconfig()){
            listConfigFiles();
            getConfigName(x);
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
