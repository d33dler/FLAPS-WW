package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.menu.MenuTree;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.HashMap;
import java.util.Scanner;

public class Ncpinteraction {
    public static class Npcconv implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            System.out.println("Hey, unknown entity, are you friendly?");
            lifeCheck(x);
        }
    }

    public static class Npcatt implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            if (lifeCheck(x)) {
                return;
            }
            System.out.println(" x) Confirm attack \n (back) Return");
            String input = in.nextLine();
            if (input.equals("back")) {
                return;
            }
            HashMap<String, GameCommands> cmenu = menuTr.getSubmenus().get("c").getSubmenus().get(input).getMenunode();
            Combat initFight = new Combat();
            initFight.duel(x, x.getLocation().getNpc(), cmenu, in, menuTr);
        }

        public static class Attack implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                System.out.println("Engaging enemy. . .\n ");
                NPC foe = x.getLocation().getNpc();
                foe.setHealth(foe.getHealth() - x.getDamage());
                x.setHealth(x.getHealth() - foe.getDamage());
            }
        }

        public static class Defend implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                NPC foe = x.getLocation().getNpc();
                foe.setHealth(foe.getHealth()-1);
                x.setHealth(x.getHealth() - foe.getDamage() / 3);
            }
        }

        public static class Flee implements GameCommands {
            @Override
            public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
                x.setLocation(x.getLocation().getDoors().get(0).getExit());
                x.setFlee(true);
            }
        }
    }

    public static class Npctrade implements GameCommands {
        @Override
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
            System.out.println("Wanna trade? ");
        }
    }

    public static boolean lifeCheck(Player x) {
        if (x.getLocation().getNpc().getHealth() <= 0) {
            System.out.println("Its avatar is not functioning. It is not responding.\n");
            return true;
        } else {
            return false;
        }
    }
}
