package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Room;

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

    public DoorBuilder clr(DoorcolorsDB color) {
        this.color = color;
        return this;
    }
    public DoorBuilder type(String type){
        this.dtype = type;
        return this;
    }

    public Door create() {
        return new Door(this);
    }
    public UltraDoor createUd(){ return new UltraDoor(this);}
    public SecureDoor createSd() {return new SecureDoor(this);}
    public CloneDoor createCd(){return new CloneDoor(this);}

    // public Prmtdoor(boolean open) {
    //     this.open = open;
    // }
}
