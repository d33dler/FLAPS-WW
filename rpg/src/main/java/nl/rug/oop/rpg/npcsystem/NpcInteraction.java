package nl.rug.oop.rpg.npcsystem;

import java.util.*;
import java.lang.reflect.*;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;


public class NpcInteraction extends WorldInteraction implements PlayerNpcAction {
    Typewriter tw = new Typewriter();

    public NpcInteraction() {
    }
    public void engageNpc(Player x) {
        Scanner in = x.getRdtxt();
        if (lifeCheck(x)) {
            return;
        }
        System.out.println(" x) Confirm attack \n (back) Return");
        String input = in.nextLine();
        if (input.equals("back")) {
            return;
        }
        x.setmTree(x.getmTree().getSubmenus().get(input));
        Combat initFight = new Combat();
        initFight.duel(x, x.npccontact, x.getmTree());
    }

    public void conversePlayer(Player x) {
        tw.type(x.name + ": Hey, unknown entity, are you friendly?\n");
        lifeCheck(x);
    }

    public void attackPlayer(Player x) {
        tw.poeticPause(x.name + " :",800);
        tw.type(" Engaging enemy. . .\n");
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
        tw.type(x.name + ": Wanna trade?\n ");
        lifeCheck(x);
    }

    public boolean lifeCheck(Player x) {

        if (x.getLocation().getNpc().getHealth() <= 0) {
            tw.poeticPause("SysCheck: ",1500);
            tw.type(" Its avatar is not functioning. It is not responding.\n");
            return true;
        } else {
            return false;
        }
    }
}
