package nl.rug.oop.rpg.itemsystem;

import java.util.EnumMap;

public enum ItemOptions {
    a, b, c, d;

    public static EnumMap<ItemOptions, String> getItem() {
        EnumMap<ItemOptions, String> commands = new EnumMap<>(ItemOptions.class);
        commands.put(ItemOptions.a, "   a) Inspect");
        commands.put(ItemOptions.b, "   b) Recycle");
        commands.put(ItemOptions.c, "   c) Collect");
        commands.put(ItemOptions.d, "   (back) Return");
        return commands;
    }
}
