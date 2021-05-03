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
        WorldInteraction winter = new WorldInteraction();
        Scanner in = x.getRdtxt();
        Method option;
        Room r = x.getLocation();
        Door inst = r.getDoors().get(0);
        inst.inspect(r);
        option = x.getmTree().getSubmenus().get("b").getMenunode().get(in.nextLine());
        try {
            option.invoke(winter,x);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public void npcCheck(Player x)
            throws InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        Scanner in = x.getRdtxt();
        WorldInteraction winter = new WorldInteraction();
        Method option;
        String input;
        Room r = x.getLocation();
        r.getNpc().inspect(r);
        EnumMap<NpcOptions, String> npcint = NpcOptions.getCompany();
        do {
            npcint.values().forEach(System.out::println);
            input = in.nextLine();
            option = x.getmTree().getSubmenus().get("c").getMenunode().get(input);
            Object interaction = winter.getActionType(option);
            try {
                option.invoke(interaction,x);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } while (!input.equals("back") && r.getNpc().getHealth() > 0 && !x.isFlee());
        x.setFlee(false);
    }


    public void enterDoor(Player x) {
        Scanner in = x.getRdtxt();
        System.out.println(": Choose a door :");
        telePort(x, x.getLocation().getDoors().get(0), in.nextInt() - 1);
    }

    public void telePort(Player x, Door inst, int n) {
        inst.interact(x, n);
    }


    public void goBack(Player x) {
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
