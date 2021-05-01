package nl.rug.oop.rpg.itemsystem;

public enum Holders {
    f("steel case"),
    x("aluminium case"),
    n("strange container, made of an unknown material type."),
    s("borosylicate crate"),
    q("polycarbonate crate"),
    z("iridium box"),
    a("chromium box"),
    k("aluminium box"),
    i("osmium crate");
    private final String name;

    Holders(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
