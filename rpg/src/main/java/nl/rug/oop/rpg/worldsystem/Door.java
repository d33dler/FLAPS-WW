package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;

public class Door implements Inspectable, Interactable {
    protected DoorcolorsDb color;
    protected Room exit, enter;

    public Door(PrmtDoor parameters) {
        this.color = parameters.color;
        this.exit = parameters.exit;
        this.enter = parameters.enter;
    }

    public void inspect(Room r) {
        //insert poetic analysis typewriter effect
        for (int i = 0; i < r.doors.size(); i++) {
            System.out.println(" Door " + (i+1) + " is " + r.doors.get(i).color);
        }
        System.out.println("(y/Y)   Access a portal? ");
        System.out.println("(back)  Return");
    }

    public Room getExit() {
        return exit;
    }

    public Room getEnter() {
        return enter;
    }

    public DoorcolorsDb getColor() {
        return color;
    }

    public void setEnter(Room enter) {
        this.enter = enter;
    }

    @Override
    public void interact(Player ava, int x) {
        Room check;
        Room r = ava.getLocation();
        check = r.doors.get(x).enter;
        if (!check.id.equals(r.id)) {
            r = r.doors.get(x).enter;
        } else {
            r = r.doors.get(x).exit;
        }
        ava.setLocation(r);
        System.out.println("Entering portal...\n");
    }
}
