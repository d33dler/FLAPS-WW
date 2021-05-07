package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.*;

public class SavingSystem implements Save, Serializable {
    private final static long serialVersionUID = 3292L;
    protected Player player;

    public void save(Player x) {
        try {
            FileOutputStream fstream = new FileOutputStream("savedgames/" + x.getSavefile() + ".ser");
            ObjectOutputStream store = new ObjectOutputStream(fstream);
            store.writeObject(x);
            store.flush();
            store.close();
            fstream.close();
            System.out.println("Save was created successfully");
        } catch (Exception e) {
            System.out.println("error saving");
        }
    }

    public Player load(Player x) {
        try {
            FileInputStream fstream = new FileInputStream("savedgames/" + x.getSavefile() + ".ser");
            ObjectInputStream objectStream = new ObjectInputStream(fstream);
            this.player = (Player) objectStream.readObject();
            objectStream.close();
            fstream.close();
            System.out.println("Save was loaded successfully");
        } catch (Exception e) {
            System.out.println("Error loading the save.");
            return x;
        }
        transferTransientData(player, x);
        return player;
    }

    public void setupLoad(Player x) {
        x.setLoad(true);
    }

    public void transferTransientData(Player player, Player x) {
        player.setmTree(x.getmTree());
        player.setTw(x.getTw());
        player.setWinter(x.getWinter());
        player.setRdtxt(x.getRdtxt());
    }

    public Player updatePlayer(Player x) {
        if (x.isLoadfile()) {
            x = load(x);
            x.setLoad(false);
        }
        return x;
    }
}
