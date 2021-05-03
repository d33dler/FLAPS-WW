package nl.rug.oop.rpg.npcsystem;

import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;


public class NpcInteraction extends WorldInteraction implements PlayerNpcAction {
    public NpcInteraction() {
    }

    public void engageNpc(Player x) throws InvocationTargetException, IllegalAccessException {
        Scanner in = x.getRdtxt();

        if (lifeCheck(x)) {
            return;
        }
        System.out.println(" x) Confirm attack \n (back) Return");
        String input = in.nextLine();
        if (input.equals("back")) {
            return;
        }
        HashMap<String, Method> cmenu = x.getmTree().getSubmenus().get("c").getSubmenus().get(input).getMenunode();
        Combat initFight = new Combat();
        try {
            initFight.duel(x, x.getLocation().getNpc(), cmenu, in, x.getmTree());
        } catch (NoSuchMethodException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void conversePlayer(Player x) {
        System.out.println("Hey, unknown entity, are you friendly?");
        lifeCheck(x);
    }

    public void attackPlayer(Player x) {
        System.out.println("Engaging enemy. . .\n ");
        NPC foe = x.getNpccontact();
        foe.setHealth(foe.getHealth() - x.getDamage());
        x.setHealth(x.getHealth() - foe.getDamage());
    }

    public void defendPlayer(Player x) {
        NPC foe = x.getLocation().getNpc();
        foe.setHealth(foe.getHealth() - 1);
        x.setHealth(x.getHealth() - foe.getDamage() / 3);
    }

    public void fleePlayer(Player x) {
        x.setLocation(x.getLocation().getDoors().get(0).getExit());
        x.setNpccontact(x.getLocation().getNpc());
        x.setFlee(true);
    }

    public void tradePlayer(Player x) {
        System.out.println("Wanna trade? ");
    }

    public boolean lifeCheck(Player x) {

        if (x.getLocation().getNpc().getHealth() <= 0) {
            System.out.println("Its avatar is not functioning. It is not responding.\n");
            return true;
        } else {
            return false;
        }
    }
}
