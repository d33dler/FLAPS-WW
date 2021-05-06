package nl.rug.oop.rpg.menu.options;

import java.util.EnumMap;

public enum CombatMenuOptions {
    a, b, c;

    public static EnumMap<CombatMenuOptions, String> setMoves() {
        EnumMap<CombatMenuOptions, String> commands = new EnumMap<>(CombatMenuOptions.class);
        commands.put(CombatMenuOptions.a, "   a) Attack");
        commands.put(CombatMenuOptions.b, "   b) Defend");
        commands.put(CombatMenuOptions.c, "   c) Flee");
        return commands;
    }
}
