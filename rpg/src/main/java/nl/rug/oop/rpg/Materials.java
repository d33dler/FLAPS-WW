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
enum Holders {
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
    a, b, c;
    public static EnumMap<Cmbtoptions,String> setMoves() {
        EnumMap<Cmbtoptions, String> commands = new EnumMap<>(Cmbtoptions.class);
        commands.put(Cmbtoptions.a, "   a) Attack");
        commands.put(Cmbtoptions.b, "   b) Defend");
        commands.put(Cmbtoptions.c, "   c) Flee");
        return commands;
    }
}

enum ItemOptions {
    a, b, c,d;
    public static EnumMap<ItemOptions,String> getItem() {
        EnumMap<ItemOptions, String> commands = new EnumMap<>(ItemOptions.class);
        commands.put(ItemOptions.a, "   a) Inspect");
        commands.put(ItemOptions.b, "   b) Recycle");
        commands.put(ItemOptions.c, "   c) Collect");
        commands.put(ItemOptions.d, "   (back) Return");
        return commands;
    }
}

enum Trading {
    n,a,b;

}

enum InventoryOpt {
   n, a,b,c,d;
    public static EnumMap<InventoryOpt, String> getInv() {
        EnumMap<InventoryOpt, String> commands = new EnumMap<>(InventoryOpt.class);
        commands.put(InventoryOpt.n, " List: ");
        commands.put(InventoryOpt.a, "   w) Weapons");
        commands.put(InventoryOpt.b, "   c) Consumables");
        commands.put(InventoryOpt.c, "   (back) Return");
        return commands;
    }
}
enum InvConsumOptions {
        a,b,c,d;
    public static EnumMap<InvConsumOptions, String> getSubInv() {
        EnumMap<InvConsumOptions, String> commands = new EnumMap<>(InvConsumOptions.class);
        commands.put(InvConsumOptions.a, "   a) Consume");
        commands.put(InvConsumOptions.b, "   b) Recycle");
        commands.put(InvConsumOptions.c, "   c) Throw away");
        commands.put(InvConsumOptions.d, "   (back) Return");
        return commands;
    }
}
enum InvWeaponOptions {
    a,b,c,d;
    public static EnumMap<InvWeaponOptions, String> getSubInv() {
        EnumMap<InvWeaponOptions, String> commands = new EnumMap<>(InvWeaponOptions.class);
        commands.put(InvWeaponOptions.a, "   a) Equip");
        commands.put(InvWeaponOptions.b, "   b) Recycle");
        commands.put(InvWeaponOptions.c, "   c) Throw away");
        commands.put(InvWeaponOptions.d, "   (back) Return");
        return commands;
    }
}
