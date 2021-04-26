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
        for (int i = 0; i < r.doors.size(); i++) {
            System.out.println(" Door " + i + " is " + r.doors.get(i).color);
        }
        System.out.println("(8) Back");
    }

    @Override
    public void interact(Player ava) {
        Scanner rdtxt = new Scanner(System.in);
        int input = rdtxt.nextInt();
        if (input == 8) {
            return;
        }
        Room check;
        Room r = ava.location;
        check = r.doors.get(input).enter;
        if (!check.id.equals(r.id)) {
            r = r.doors.get(input).enter;
        } else {
            r = r.doors.get(input).exit;
        }
        ava.location = r;
        System.out.println("Entering portal...\n");
    }
}
