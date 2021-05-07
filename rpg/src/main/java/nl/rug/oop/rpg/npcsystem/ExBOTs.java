package nl.rug.oop.rpg.npcsystem;

public enum ExBOTs {
    cyb("ExBOT1000", 0, 0),
    sb("ExBOT2000", 0, 0),
    sent("ExBOT3000", 0, 0);

    private final String spname;
    private final int damage, health;

    ExBOTs(String spname, int health, int damage) {
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
