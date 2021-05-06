package nl.rug.oop.rpg.menu;
import java.util.EnumMap;

public enum SaveMenuOptions {
    a, b,c,d,e;

    public static EnumMap<SaveMenuOptions, String> getSaveOpt() {
        EnumMap<SaveMenuOptions, String> commands = new EnumMap<>(SaveMenuOptions.class);
        commands.put(SaveMenuOptions.a, "a) QuickSave");
        commands.put(SaveMenuOptions.b, "b) QuickLoad");
        commands.put(SaveMenuOptions.c, "c) Save");
        commands.put(SaveMenuOptions.d, "d) Load");
        commands.put(SaveMenuOptions.e, "(back) Return ");
        return commands;
    }
}