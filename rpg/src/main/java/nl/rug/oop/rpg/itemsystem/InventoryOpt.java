package nl.rug.oop.rpg.itemsystem;

import java.util.EnumMap;

public enum InventoryOpt {
    n, a, b, c, d;

    public static EnumMap<InventoryOpt, String> getInv() {
        EnumMap<InventoryOpt, String> commands = new EnumMap<>(InventoryOpt.class);
        commands.put(InventoryOpt.n, " List: ");
        commands.put(InventoryOpt.a, "   w) Weapons");
        commands.put(InventoryOpt.b, "   c) Consumables");
        commands.put(InventoryOpt.c, "   (back) Return");
        return commands;
    }
}
