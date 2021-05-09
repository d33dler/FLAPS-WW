package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.doors.Door;

import java.lang.reflect.*;
import java.util.Scanner;

/**
 * Class holding methods concerning various types of interaction with the world objects;
 */
public class WorldInteraction {

    public WorldInteraction() {
    }

    public void roomCheck(Player x) {
        Room r = x.getLocation();
        r.inspect(x);
    }

    /**
     * @param player is used to get the location where doors are checked
     *               players doorFocus is updated and the doors are inspected in a loop. Offering
     *               distinct description - as we call upon inspect() method which is overrided
     *               by all Door subtypes.
     */
    public void doorCheck(Player player) {
        Room r = player.getLocation();
        System.out.println("You found :");
        for (int i = 0; i < r.getDoors().size(); i++) {
            player.setDoorFocus(r.getDoors().get(i));
            player.setIntIn(i);
            player.getDoorFocus().inspect(player);
            System.out.println("\n");
        }
        System.out.println("(y/Y)   Access a portal? ");
        System.out.println("(back)  Return");
    }

    /**
     * @param player delivers the location where we are inspecting an NPC
     *               the player object's focus field value is set to the NPC object being inspected
     *               This field value is further used to interact with the npc.
     *               Calling upon inspect() yields different textual descriptions based on NPC subtypes subclasses
     *               which override the inspect() method of the parent class.
     */
    public void npcCheck(Player player) {
        Room r = player.getLocation();
        NPC npc = r.getNpc();
        player.setNpcFocus(npc);
        npc.inspect(player);
    }

    /**
     * @param player used to promt the user to choose a door and sets the player doorFocus on the the
     *               chosen door , initiating the update of the players location and his contact with usage
     *               of the doors through the interact() method which is overriden by all Door subclasses
     *               hence giving different behaviour results.
     */
    public void enterDoor(Player player) {
        Scanner in = player.getReadTxt();
        Room r = player.getLocation();
        System.out.println(": Choose a door :");
        int input = in.nextInt() - 1;
        try {
            player.setDoorFocus(r.getDoors().get(input));

        } catch (IndexOutOfBoundsException e) {
            player.gettW().type("Unknown door number, please try again\n");
            player.setmTree(player.getmTree().getRoot());
            return;
        }
        telePort(player, player.getLocation().getDoors().get(input), input);
        player.setNpccontact(player.getLocation().getNpc());
        player.setmTree(player.getmTree().getRoot());
        player.readTxt.nextLine();
    }

    public void telePort(Player x, Door door, int n) {
        x.setIntIn(n);
        if (!x.isRabbit()) {
            x.setTravelBuff(x.getTravelBuff() + 1);
        }
        door.interact(x);
    }

    /**
     * Jump to parent node of the current menu node
     */
    public void goBack(Player player) {
        player.setmTree(player.getmTree().getRoot());
    }

    /**
     * @param action identifies the class where the method for interaction originates from
     *               and gives a new instance given by the NoVarAg constructor in the class.
     */
    public static Object getActionType(Method action) throws Exception {
        Class<?> interaction = action.getDeclaringClass();
        Constructor<?> gen = interaction.getConstructor();
        return gen.newInstance();
    }
}
