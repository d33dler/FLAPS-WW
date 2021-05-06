package nl.rug.oop.rpg.game;
import nl.rug.oop.rpg.menu.GameMenu;
import nl.rug.oop.rpg.worldsystem.Player;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class SavingSystem implements Serializable {
    private final static long serialVersionUID = 3292L;
    Player player;
    GameMenu gmenu = new GameMenu();
    public void quickSave(Player x) {
        File saveDir = new File("savedgames");
        saveDir.mkdir();
        try {
            FileOutputStream fstream = new FileOutputStream("savedgames/quicksave.ser");
            ObjectOutputStream store = new ObjectOutputStream(fstream);
            store.writeObject(x);
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
        this.player = (Player) objectStream.readObject();
        objectStream.close();

        grantAssets(player, x);
        try {
            gmenu.renderMenu(player);
        } catch (InvocationTargetException
                | NoSuchMethodException
                | InstantiationException
                | IllegalAccessException e) {
            System.out.println("error loading save");
        }
    }
    public void grantAssets(Player player, Player x)  {
        player.setmTree(x.getmTree());
        player.setTw(x.getTw());
        player.setWinter(x.getWinter());
        player.setRdtxt(x.getRdtxt());
    }
}
