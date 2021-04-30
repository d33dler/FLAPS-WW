package nl.rug.oop.rpg;

import java.util.HashMap;
import java.util.Scanner;

public interface GameCommands {
    void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr);
}
