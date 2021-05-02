package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import java.util.HashMap;
import java.util.Scanner;

public class ItemInteraction {

    public static class ItemInspect implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            r.getLoot().inspect(r);
        }
    }

    public static class ItemPick implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            r.getLoot().interact(x, 0);
        }
    }

    public static class ItemConsume implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            x.setHold(r.getLoot());
            r.getLoot().Recycle(x);
        }
    }

    public static class ItemInvThrow implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            r.getLoot().interact(x, 0);
        }
    }

    public static class ItemInvConsume implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            x.setHold(r.getLoot());
            r.getLoot().Recycle(x);

        }
    }

    public static class ItemInvEquip implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            Room r = x.getLocation();
            x.setHold(r.getLoot());
            r.getLoot().Recycle(x);

        }
    }

    public static class ItemInvRecycle implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {

        }

    }

}
