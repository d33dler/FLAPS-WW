package nl.rug.oop.rpg.npcsystem;

import java.util.EnumMap;

public enum NpcOptions {
    n, a, b, c, d;

    public static EnumMap<NpcOptions, String> getCompany() {
        EnumMap<NpcOptions, String> commands = new EnumMap<>(NpcOptions.class);
        commands.put(NpcOptions.n, "Initialize: ");
        commands.put(NpcOptions.a, "   a) conversation");
        commands.put(NpcOptions.b, "   b) trade");
        commands.put(NpcOptions.c, "   c) combat");
        commands.put(NpcOptions.d, "   (back) Return");
        return commands;
    }
}
