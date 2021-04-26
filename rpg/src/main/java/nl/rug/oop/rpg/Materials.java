package nl.rug.oop.rpg;

import java.util.EnumMap;

public enum Materials {
}

enum DoorcolorsDb {
    red(" red"),
    blue(" blue"),
    black(" black"),
    gray(" gray"),
    cyan(" cyan");
    private final String cname;

    DoorcolorsDb(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }
}

enum Attr1room {
    r(" ruined "),
    l(" large "),
    n(" narrow "),
    wl(" well-lighted "),
    d(" dark "),
    s(" strange ");

    private final String att1;

    Attr1room(String att1) {
        this.att1 = att1;
    }

    public String getAtt1() {
        return att1;
    }
}

enum Attr2room {
    r(" full of hi-tech operation tables."),
    l(" with quantum computers."),
    n(" and it's almost empty."),
    wl(" with working stations."),
    d(" with a pile of wires."),
    s(" and there is a strong magnetic field here."),
    s2(" and there is a strong magnetic field here, as well."),
    s3(" and there is a strong magnetic field here. Lot's of them, eh?.");
    private final String att2;

    Attr2room(String att2) {
        this.att2 = att2;
    }

    public String getAtt2() {
        return att2;
    }
}

enum WeaponsDb {
    crowbar("Crowbar", 9),
    wrench("Wrench", 8),
    laser_gun("Laser gun", 25),
    gauss("Gauss Rifle", 33),
    pl_gun("Plasma Gun", 28);
    private final String wname;
    private final int dmg;

    WeaponsDb(String wname, int dmg) {
        this.wname = wname;
        this.dmg = dmg;
    }//inspect constr application

    public String getWname() {
        return wname;
    }

    public int getDmg() {
        return dmg;
    }
}

enum ConsumablesDb {
    qfet("QFE-transistor", 7),
    carc("Paladium cell", 10),
    cpu("Q-Processors", 14),
    wr("Wires", 5),
    tp("Titanium", 11);
    private final String consid;
    private final int health;

    ConsumablesDb(String consid, int health) {
        this.consid = consid;
        this.health = health;
    }//inspect constructor app

    public String getConsid() {
        return consid;
    }

    public int getHealth() {
        return health;
    }
}

enum SpeciesDb {
    cyb("Cyborg doll", 40, 10),
    sb("Sentry bot", 50, 12),
    sent("Sentinel", 80, 24),
    fl("Flesher", 18, 10);
    private final String spname;
    private final int damage, health;

    SpeciesDb(String spname, int health, int damage) {
        this.spname = spname;
        this.health = health;
        this.damage = damage;
    }

    public String getSpname() {
        return spname;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }
}

enum Explrptions {
    a, b, c, d, e;
    public static EnumMap<Explrptions, String> getExmn() {
        EnumMap<Explrptions, String> commands = new EnumMap<>(Explrptions.class);
        commands.put(Explrptions.a, "a) Scan room environment");
        commands.put(Explrptions.b, "b) Check available portals");
        commands.put(Explrptions.c, "c) Check for other beings");
        commands.put(Explrptions.d, "d) Search room for resources");
        commands.put(Explrptions.e, "e) Inventory ");
        return commands;
    }
}

enum Npcinteract {
    n, a, b, c, d;
    public static EnumMap<Npcinteract, String > getCompany(){
        EnumMap<Npcinteract,String> commands = new EnumMap<>(Npcinteract.class);
        commands.put(Npcinteract.n, "Initialize: ");
        commands.put(Npcinteract.a, "   a) conversation");
        commands.put(Npcinteract.b, "   b) trade");
        commands.put(Npcinteract.c, "   c) combat");
        commands.put(Npcinteract.d, "   (back) Return");
        return commands;
    }
}

enum Cmbtoptions {
    a("a) Attack"),
    b("b) Defend"),
    c("c) Flee");
    private final String option;

    Cmbtoptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}

enum Trading {
    n,a,b;

}

enum Inventoryopt {

}

enum Items {

}