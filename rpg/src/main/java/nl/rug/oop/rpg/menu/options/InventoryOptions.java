package nl.rug.oop.rpg.menu.options;

import java.util.EnumMap;

public enum InventoryOptions {
    n, a, b, c, d;

    public static EnumMap<InventoryOptions, String> getInv() {
        EnumMap<InventoryOptions, String> commands = new EnumMap<>(InventoryOptions.class);
        commands.put(InventoryOptions.n, " List: ");
        commands.put(InventoryOptions.a, "   w) Weapons");
        commands.put(InventoryOptions.b, "   c) Consumables");
        commands.put(InventoryOptions.c, "   (back) Return");
        return commands;
    }
}
