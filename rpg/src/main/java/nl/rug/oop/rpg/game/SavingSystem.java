package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.World;

import java.io.*;

public class SavingSystem {

    public void quickSave(Player x) {
        File saveDir = new File("savedgames");
        saveDir.mkdir();
        try {
            FileOutputStream fstream = new FileOutputStream("savedgames/quicksave.ser");
            ObjectOutputStream store = new ObjectOutputStream(fstream);
            store.writeObject(x);
            store.writeObject(x.getMap());
            store.flush();
            store.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("error saving");
        }
    }

    public void quickLoad(Player x)
            throws ClassNotFoundException,
            IOException {
        FileInputStream fstream = new FileInputStream("savedgames/quicksave.ser");
        ObjectInputStream objectStream = new ObjectInputStream(fstream);
        x = (Player) objectStream.readObject();
        x.setMap((World) objectStream.readObject());
        //SaveData load = new SaveDataBuilder().player(splayer).map(smap).loadSave();
        Gameplay load = new Gameplay();
        load.Explore(x);
    }
    public Player loadData(Player x, SaveData sdata) throws CloneNotSupportedException {
        x = sdata.player.clone();
        return x;
    }
}
