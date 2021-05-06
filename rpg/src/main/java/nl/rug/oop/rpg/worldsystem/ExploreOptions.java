package nl.rug.oop.rpg.worldsystem;

import java.util.EnumMap;

public enum ExploreOptions {
    a, b, c, d, e,f;

    public static EnumMap<ExploreOptions, String> getExmn() {
        EnumMap<ExploreOptions, String> commands = new EnumMap<>(ExploreOptions.class);
        commands.put(ExploreOptions.a, "a) Scan room environment");
        commands.put(ExploreOptions.b, "b) Check available portals");
        commands.put(ExploreOptions.c, "c) Check for other beings");
        commands.put(ExploreOptions.d, "d) Search room for resources");
        commands.put(ExploreOptions.e, "e) Inventory\n");
        commands.put(ExploreOptions.f, "f) Pause \n");
        return commands;
    }
}
