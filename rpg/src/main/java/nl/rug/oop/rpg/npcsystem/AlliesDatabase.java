package nl.rug.oop.rpg.npcsystem;

public enum AlliesDatabase {
    cyb("Dijkstra Agent", 140, 5),
    sb("Travelling Salesman", 7, 8),
    sent("Neumann Machine", 90, 10),
    fl("Al-Khwarizmi Android ", 18, 10),
    qwq("C.A.R. Hoare ", 18, 10),
    ads("Contingency Handler ", 100000, 100),
    gg("Turing Automaton ", 100, 10);
    private final String spname;
    private final int damage, health;

    AlliesDatabase(String spname, int health, int damage) {
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
