package nl.rug.oop.rpg.menu.options;

import java.util.EnumMap;

public enum NpcMenuOptions {
    n, a, b, c, d;

    public static EnumMap<NpcMenuOptions, String> getCompany() {
        EnumMap<NpcMenuOptions, String> commands = new EnumMap<>(NpcMenuOptions.class);
        commands.put(NpcMenuOptions.n, "Initialize: ");
        commands.put(NpcMenuOptions.a, "   a) conversation");
        commands.put(NpcMenuOptions.b, "   b) trade");
        commands.put(NpcMenuOptions.c, "   c) combat");
        commands.put(NpcMenuOptions.d, "   (back) Return");
        return commands;
    }
}
