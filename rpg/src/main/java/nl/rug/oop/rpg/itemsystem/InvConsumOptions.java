package nl.rug.oop.rpg.itemsystem;

import java.util.EnumMap;

public enum InvConsumOptions {
    a, b, c, d;

    public static EnumMap<InvConsumOptions, String> getSubInv() {
        EnumMap<InvConsumOptions, String> commands = new EnumMap<>(InvConsumOptions.class);
        commands.put(InvConsumOptions.a, "   a) Consume");
        commands.put(InvConsumOptions.b, "   b) Recycle");
        commands.put(InvConsumOptions.c, "   c) Throw away");
        commands.put(InvConsumOptions.d, "   (back) Return");
        return commands;
    }
}
