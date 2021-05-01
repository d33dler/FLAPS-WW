package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.HashMap;
import java.util.Scanner;

public interface GameCommands {
    void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr);
}
