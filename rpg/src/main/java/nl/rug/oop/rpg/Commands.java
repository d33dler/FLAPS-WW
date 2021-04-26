package nl.rug.oop.rpg;

import java.util.HashMap;
import java.util.Scanner;

public interface Commands {
    void exec(Player x, Scanner in, HashMap<String, Commands> menu, MenuTree menuTr);
}
