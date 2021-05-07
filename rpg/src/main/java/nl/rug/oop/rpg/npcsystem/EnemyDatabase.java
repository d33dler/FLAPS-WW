package nl.rug.oop.rpg.npcsystem;

public enum EnemyDatabase {
    cyb("Cyborg doll", 40, 10),
    sb("Sentry bot", 50, 12),
    sent("Sentinel", 80, 24),
    fl("Flesher", 18, 10);
    private final String spname;
    private final int damage, health;

    EnemyDatabase(String spname, int health, int damage) {
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
