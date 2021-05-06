package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.lang.reflect.*;

import java.util.Scanner;

public class WorldInteraction {
    public WorldInteraction() {
    }

    public void roomCheck(Player x) {
        Room r = x.getLocation();
        r.inspect(x);
    }


    public void doorCheck(Player x) {
        Room r = x.getLocation();
        System.out.println("You found :");
        for (int i = 0; i < r.getDoors().size(); i++) {
            x.setFocus(r.getDoors().get(i));
            x.setIntin(i);
            x.getFocus().inspect(x);
            System.out.println("\n");
        }
        System.out.println("(y/Y)   Access a portal? ");
        System.out.println("(back)  Return");
    }

    public void npcCheck(Player x) {
        Room r = x.getLocation();
        r.getNpc().inspect(x);
    }

    public void enterDoor(Player x) {
        Scanner in = x.getRdtxt();
        Room r = x.getLocation();
        System.out.println(": Choose a door :");
        int input = in.nextInt() - 1;
        x.rdtxt.nextLine();
        try {
            x.setFocus(r.getDoors().get(input));
        } catch (IndexOutOfBoundsException e) {
            x.getTw().type("Unknown door number, please try again\n");
            x.setmTree(x.getmTree().getRoot());
            return;
        }
        telePort(x, x.getLocation().getDoors().get(input), input);

        x.setNpccontact(x.getLocation().getNpc());
        x.setmTree(x.getmTree().getRoot());
    }

    public void telePort(Player x, Door door, int n) {
        x.setIntin(n);
        x.setFocus(door);
        if (!x.isRabbit()) {
            x.setTravel(x.getTravelBuff() + 1);
        }
        door.interact(x);
    }

    public void goBack(Player x) {
        x.setmTree(x.getmTree().getRoot());
    }

    public static Object getActionType(Method action)
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Class<?> interaction = action.getDeclaringClass();
        Constructor<?> gen = interaction.getConstructor();
        return gen.newInstance();
    }
}
