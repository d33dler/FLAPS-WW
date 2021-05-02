package nl.rug.oop.rpg.game;

import java.util.EnumMap;

public enum CombatOptions {
    a, b, c;

    public static EnumMap<CombatOptions, String> setMoves() {
        EnumMap<CombatOptions, String> commands = new EnumMap<>(CombatOptions.class);
        commands.put(CombatOptions.a, "   a) Attack");
        commands.put(CombatOptions .b, "   b) Defend");
        commands.put(CombatOptions.c, "   c) Flee");
        return commands;
    }
}
