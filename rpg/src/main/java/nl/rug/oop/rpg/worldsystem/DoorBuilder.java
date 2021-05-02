package nl.rug.oop.rpg.worldsystem;

public class DoorBuilder extends Door {
    /* key- requires hashmap sharing key with door*/
    public DoorBuilder exit(Room exit) {
        this.exit = exit;
        return this;
    }

    public DoorBuilder enter(Room enter) {
        this.enter = enter;
        return this;
    }

    public DoorBuilder clr(DoorcolorsDb color) {
        this.color = color;
        return this;
    }

    public Door create() {
        return new Door(this);
    }

    // public Prmtdoor(boolean open) {
    //     this.open = open;
    // }
}
