package nl.rug.oop.rpg.game.savingsys;

import nl.rug.oop.rpg.worldsystem.Player;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;


public class SavingSystem implements Save, Serializable {
    private final static long serialVersionUID = 3292L;
    Player player;

    public void save(Player x) {
        File saveDir = new File("savedgames");
        saveDir.mkdir();
        try {
            FileOutputStream fstream = new FileOutputStream("savedgames/" + x.getSavefile() + ".ser");
            ObjectOutputStream store = new ObjectOutputStream(fstream);
            store.writeObject(x);
            store.flush();
            store.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("error saving");
        }
    }

    public Player load(Player x)
            throws IOException,
            ClassNotFoundException {
        FileInputStream fstream = new FileInputStream("savedgames/" + x.getSavefile() + ".ser");
        ObjectInputStream objectStream = new ObjectInputStream(fstream);
        this.player = (Player) objectStream.readObject();
        objectStream.close();
        fstream.close();
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

    public Player updatePlayer(Player x) throws
            IOException,
            ClassNotFoundException {
        if (x.isLoadfile()) {
            x = load(x);
            x.setLoad(false);
        }
        return x;
    }

    public void listSaveFiles() {
        File save = new File("savedgames/");
        FilenameFilter serf = (file, s) -> s.endsWith(".ser");
        File[] list = save.listFiles(serf);

        if (list != null) {
            Stream<File> stream = Stream.of(list);
            stream.forEach(str -> {
                try {
                    System.out.println(" ■ " + str.getName() + " ─ Created on : " + getCreateDate(str.getName()));
                } catch (IOException e) {
                    System.out.println("Error fetching file metadata");
                }
            });
        } else {
            System.out.println("Save files path is empty");
        }
    }

    public String getCreateDate(String name) throws IOException {
        Path files = Paths.get("/home/radubereja/Desktop/Object-Oriented Programming/a0/2021_Team_060/rpg/savedgames/" + name);
        BasicFileAttributes att = Files.readAttributes(files, BasicFileAttributes.class);
        return String.valueOf(att.creationTime());
    }
}
