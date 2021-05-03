package nl.rug.oop.rpg.npcsystem;
import java.lang.reflect.*;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.HashMap;
import java.util.Scanner;

public interface PlayerNpcAction {
    void engageNpc(Player x) throws InvocationTargetException, IllegalAccessException;
    void conversePlayer(Player x);
    void defendPlayer(Player x);
    void attackPlayer(Player x);
    void fleePlayer(Player x);
    void tradePlayer(Player x);
    boolean lifeCheck(Player x);
}
