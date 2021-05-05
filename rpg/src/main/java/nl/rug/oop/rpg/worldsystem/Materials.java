package nl.rug.oop.rpg.worldsystem;

import java.util.EnumMap;

public enum Materials {
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
    crowbar("Crowbar", 9,6),
    wrench("Wrench", 8,7),
    laser_gun("Laser gun", 25,28),
    gauss("Gauss Rifle", 33,45),
    pl_gun("Plasma Gun", 28,30);
    private final String wname;
    private final int dmg, value;

    WeaponsDb(String wname, int dmg,int value) {
        this.wname = wname;
        this.dmg = dmg;
        this.value = value;

    }//inspect constr application

    public String getWname() {
        return wname;
    }

    public int getValue() {
        return value;
    }

    public int getDmg() {
        return dmg;
    }
}

enum ConsumablesDb {
    qfet("QFE-transistor", 7,5),
    carc("Paladium cell", 10,9),
    cpu("Q-Processors", 14,11),
    wr("Wires", 5,4),
    tp("Titanium", 11,12);
    private final String consid;
    private final int health, value;

    ConsumablesDb(String consid, int health, int value) {
        this.consid = consid;
        this.health = health;
        this.value = value;
    }//inspect constructor app

    public String getConsid() {
        return consid;
    }

    public int getHealth() {
        return health;
    }

    public int getValue() {
        return value;
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

enum Trading {
    n,a,b;

}

