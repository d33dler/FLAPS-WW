package nl.rug.oop.rpg.worldsystem;
import nl.rug.oop.rpg.npcsystem.NpcOptions;

import java.lang.reflect.*;

import java.util.EnumMap;
import java.util.Scanner;

public class WorldInteraction {
    public WorldInteraction() {
    }

    public void roomCheck(Player x) {
        Room r = x.getLocation();
        r.inspect(r);
    }


    public void doorCheck(Player x) {
        Room r = x.getLocation();
        Door inst = r.getDoors().get(0);
        inst.inspect(r);
    }

    public void npcCheck(Player x)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {

        Room r = x.getLocation();
        r.getNpc().inspect(r);
    }


    public void enterDoor(Player x) {
        Scanner in = x.getRdtxt();
        System.out.println(": Choose a door :");
        telePort(x, x.getLocation().getDoors().get(0), in.nextInt() - 1);
        x.rdtxt.nextLine();
        x.setNpccontact(x.getLocation().getNpc());
        x.setmTree(x.getmTree().getRoot());
    }

    public void telePort(Player x, Door inst, int n) {
        inst.interact(x, n);
    }


    public void goBack(Player x) {
        x.setmTree(x.getmTree().getRoot());
    }
    public Object getActionType(Method action)
            throws NoSuchMethodException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Class<?> interaction = action.getDeclaringClass();
        Constructor<?> gen = interaction.getConstructor();
        return gen.newInstance();
    }
}
