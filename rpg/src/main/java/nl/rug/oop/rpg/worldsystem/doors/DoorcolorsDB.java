package nl.rug.oop.rpg.worldsystem.doors;

import java.io.Serializable;

public enum DoorcolorsDB {
    red("red"),
    blue("blue"),
    black("black"),
    gray("gray"),
    cyan("cyan"),
    qa("framboise"),
    sd("azalea"),
    df("cornsilk"),
    fg("opaline"),
    be("roseate"),
    f("netsuke"),
    g("citrine"),
    h("persimmon"),
    i("gainsboro"),
    j("clary"),
    k("onyx"),
    l("fuchsia"),
    m("wisteria"),
    n("chartreuse"),
    o("azure"),
    p("cobalt"),
    r("olive"),
    s("emerald"),
    t("ruby"),
    v("crimson"),
    w("violet"),
    q("orchid"),
    y("amethyst"),
    z("mauve"),
    ab("sunglow"),
    ac("amber"),
    ad("aureolin"),
    ae("saffron"),
    af("beige"),
    ag("lavender"),
    ah("green"),
    ai("celeste"),
    aj("periwinkle"),
    ce("cerulean"),
    tu("turquoise"),
    ma("malachite"),
    te("terracotta"),
    ve("vermilion"),
    co("cochineal"),
    db("dragon's blood"),
    bw("brazilwood"),
    cy("cadmium yellow");


    private final String cname;

    DoorcolorsDB(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }
}
