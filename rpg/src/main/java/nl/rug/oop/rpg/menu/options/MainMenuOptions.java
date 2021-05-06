package nl.rug.oop.rpg.menu.options;

import java.util.EnumMap;

public enum MainMenuOptions {
    a, b, c, d, e;
    public static EnumMap<MainMenuOptions, String> getSaveOpt() {
        EnumMap<MainMenuOptions, String> commands = new EnumMap<>(MainMenuOptions.class);
        commands.put(MainMenuOptions.a, "a) Play");
        commands.put(MainMenuOptions.b, "b) Load from config");
        commands.put(MainMenuOptions.c, "c) Save default config");
        commands.put(MainMenuOptions.d, "d) Create config");
        commands.put(MainMenuOptions.e, "(x) Exit ");
        return commands;
    }
}

