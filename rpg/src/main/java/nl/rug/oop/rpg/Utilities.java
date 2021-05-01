package nl.rug.oop.rpg;

import nl.rug.oop.rpg.worldsystem.Player;

import java.util.*;

public abstract class Utilities {


    public static void getUserName(Player x, Scanner in) {
        System.out.println("Please authenticate yourself: ");
        x.setName(in.nextLine());
    }

}

