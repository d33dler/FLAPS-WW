package nl.rug.oop.rpg.game;

import java.util.EnumMap;

public enum Cmbtoptions {
    a, b, c;

    public static EnumMap<Cmbtoptions, String> setMoves() {
        EnumMap<Cmbtoptions, String> commands = new EnumMap<>(Cmbtoptions.class);
        commands.put(Cmbtoptions.a, "   a) Attack");
        commands.put(Cmbtoptions.b, "   b) Defend");
        commands.put(Cmbtoptions.c, "   c) Flee");
        return commands;
    }
}
