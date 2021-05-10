package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Room;

public class DoorBuilder extends Door {
    public DoorBuilder exit(Room exit) {
        this.exit = exit;
        return this;
    }

    public DoorBuilder enter(Room enter) {
        this.enter = enter;
        return this;
    }

    public DoorBuilder clr(String color) {
        this.color = color;
        return this;
    }

    public DoorBuilder type(String type) {
        this.doorType = type;
        return this;
    }

    public DoorBuilder vis(boolean vis) {
        this.visited = vis;
        return this;
    }

    public DoorBuilder open(Boolean open) {
        this.open = open;
        return this;
    }

    public Door create() {
        return new Door(this);
    }

    public RabitDoor createUd() {
        return new RabitDoor(this);
    }

    public SecureDoor createSd() {
        return new SecureDoor(this);
    }

    public CloningDoor createCd() {
        return new CloningDoor(this);
    }
}
