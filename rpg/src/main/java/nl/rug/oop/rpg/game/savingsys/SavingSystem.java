package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.*;

public class SavingSystem implements Save, Serializable {
    private final static long serialVersionUID = 3292L;
    protected Player player;

    /**
     * Parent class method for creating a save file.
     * @param player has the field value for the save file name used for both quicksave,quickload
     *              and common save,load child class method override implementations.
     */
    public void saveSave(Player player) {
        try {
            FileOutputStream fstream = new FileOutputStream("savedgames/" + player.getSaveFile() + ".ser");
            ObjectOutputStream store = new ObjectOutputStream(fstream);
            store.writeObject(player);
            store.flush();
            store.close();
            fstream.close();
            System.out.println("Save was created successfully");
        } catch (Exception e) {
            System.out.println("error saving");
        }
    }

    /**
     *Parent class method for loading a save file.
     * @param player  has the field value for the save file name used for both quicksave,quickload
     *      *              and common save,load child class method override implementations.
     * @return player instance from save file. Player holds the map (world) data.
     */
    public Player loadSave(Player player) {
        try {
            FileInputStream fstream = new FileInputStream("savedgames/" + player.getSaveFile() + ".ser");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            this.player = (Player) objectStream.readObject();
            objectStream.close();
            fstream.close();
            System.out.println("Save was loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading the save.");
            return player;
        }
        transferTransientData(this.player, player);
        return this.player;
    }

    /**
     *
     * @param player setting up the boolean trigger which is checked in the game menu loop
     *               which is checked and if true - the loading process is initiated and player object
     *               is updated.
     */
    public void setupLoad(Player player) {
        player.setLoad(true);
    }

    public void transferTransientData(Player player, Player x) {
        player.setmTree(x.getmTree());
        player.settW(x.gettW());
        player.setwInter(x.getwInter());
        player.setReadTxt(x.getReadTxt());
    }

    /**
     *
     * @param player has its internal data updated and the switch for loading data is set to false
     * @return updated player object
     */
    public Player updatePlayer(Player player) {
        if (player.isLoadFile()) {
            player = loadSave(player);
            player.setLoad(false);
        }
        return player;
    }
}
