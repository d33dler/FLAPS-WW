package nl.rug.oop.rpg.menu;
import java.util.EnumMap;

public enum SaveMenuOptions {
    a, b,c;

    public static EnumMap<SaveMenuOptions, String> getSaveOpt() {
        EnumMap<SaveMenuOptions, String> commands = new EnumMap<>(SaveMenuOptions.class);
        commands.put(SaveMenuOptions.a, "a) QuickSave");
        commands.put(SaveMenuOptions.b, "b) QuickLoad");
        commands.put(SaveMenuOptions.c, "c) Back ");
        return commands;
    }
}