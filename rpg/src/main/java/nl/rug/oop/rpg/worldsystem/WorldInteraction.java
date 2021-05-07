package nl.rug.oop.rpg.worldsystem;
import nl.rug.oop.rpg.npcsystem.NPC;
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
            x.setDoorFocus(r.getDoors().get(i));
            x.setIntin(i);
            x.getDoorFocus().inspect(x);
            System.out.println("\n");
        }
        System.out.println("(y/Y)   Access a portal? ");
        System.out.println("(back)  Return");
    }

    public void npcCheck(Player x) {
        Room r = x.getLocation();
        NPC npc = r.getNpc();
        x.setNpcFocus(npc);
        npc.inspect(x);
    }

    public void enterDoor(Player x) {
        Scanner in = x.getRdtxt();
        Room r = x.getLocation();
        System.out.println(": Choose a door :");
        int input = in.nextInt() - 1;
        try {
            x.setDoorFocus(r.getDoors().get(input));

        } catch (IndexOutOfBoundsException e) {
            x.getTw().type("Unknown door number, please try again\n");
            x.setmTree(x.getmTree().getRoot());
            return;
        }
        telePort(x, x.getLocation().getDoors().get(input), input);
        x.setNpccontact(x.getLocation().getNpc());
        x.setmTree(x.getmTree().getRoot());
        x.rdtxt.nextLine();
    }

    public void telePort(Player x, Door door, int n) {
        x.setIntin(n);
        if (!x.isRabbit()) {
            x.setTravelBuff(x.getTravelBuff() + 1);
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
