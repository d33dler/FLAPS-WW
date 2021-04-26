package nl.rug.oop.rpg;

import java.util.Scanner;

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

    @Override
    public void interact(Player ava, int x) {
        if (x == 8) {
            return;
        }
        Room check;
        Room r = ava.location;
        check = r.doors.get(x).enter;
        if (!check.id.equals(r.id)) {
            r = r.doors.get(x).enter;
        } else {
            r = r.doors.get(x).exit;
        }
        ava.location = r;
        System.out.println("Entering portal...\n");
    }
}
