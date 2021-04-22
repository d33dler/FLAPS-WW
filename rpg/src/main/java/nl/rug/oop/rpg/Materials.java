package nl.rug.oop.rpg;

public enum Materials {
}

enum ColorsDb {
    red("red"),
    blue("blue"),
    black("black"),
    gray("gray"),
    cyan("cyan");
    private final String cname;
    ColorsDb(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
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
        this.consid=consid;
        this.health=health;
    }//inspect constructor app

    public String getConsid() {
        return consid;
    }
    public int getHealth() {
        return health;
    }
}

enum Species {

}