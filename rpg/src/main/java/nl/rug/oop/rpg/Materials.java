package nl.rug.oop.rpg;

public enum Materials {
}
enum Colors {

}

enum Weapons {
    crowbar("Crowbar", 9),
    wrench("Wrench", 8),
    laser_gun("Laser gun", 25),
    gauss("Gauss Rifle",33 ),
    pl_gun("Plasma Gun", 28);

    private final String wname;
    private final int dmg;
    Weapons(String wname, int dmg) {
        this.wname = wname;
        this.dmg = dmg;
    }
    public String getWname() {
        return wname;
    }
    public int getDmg() {
        return dmg;
    }

}