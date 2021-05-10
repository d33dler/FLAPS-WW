package nl.rug.oop.rpg.menu.options;

import java.util.EnumMap;

public enum InvWeaponOptions {
    a, b, c, d;

    public static EnumMap<InvWeaponOptions, String> getSubInv() {
        EnumMap<InvWeaponOptions, String> commands = new EnumMap<>(InvWeaponOptions.class);
        commands.put(InvWeaponOptions.a, "   a) Equip");
        commands.put(InvWeaponOptions.b, "   b) Recycle");
        commands.put(InvWeaponOptions.c, "   c) Throw away");
        commands.put(InvWeaponOptions.d, "   (back) Return");
        return commands;
    }
}
