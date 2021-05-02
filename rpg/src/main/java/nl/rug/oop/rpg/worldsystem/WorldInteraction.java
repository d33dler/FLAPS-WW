package nl.rug.oop.rpg.worldsystem;

import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.menu.MenuTree;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Scanner;

public class WorldInteraction {

    public static class Roomcheck implements GameCommands {

       @Override
       public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
           Room r = x.getLocation();
           r.inspect(r);
       }
   }

    public static class Doorcheck implements GameCommands {

       @Override
       public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
           GameCommands option;
           Room r = x.getLocation();
           Door inst = r.getDoors().get(0);
           inst.inspect(r);
           option = menu.get(in.nextLine());
           option.exec(x, in, menu, menuTr);
       }
   }

    public static class Npccheck implements GameCommands {

       @Override
       public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
           GameCommands option;
           String input;
           Room r = x.getLocation();
           r.getNpc().inspect(r);
           EnumMap<Npcinteract, String> npcint = Npcinteract.getCompany();
           do {
               npcint.values().forEach(System.out::println);
               input = in.nextLine();
               option = menu.get(input);
               option.exec(x, in, menu, menuTr);
           } while (!input.equals("back") && r.getNpc().getHealth() > 0 && !x.isFlee());
           x.setFlee(false);
       }
   }

    public static class enterDoor implements GameCommands {
       @Override
       public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
           System.out.println(": Choose a door :");
           telePort(x, x.getLocation().getDoors().get(0), in.nextInt() - 1);
       }

       public void telePort(Player x, Door inst, int n) {
           inst.interact(x, n);
       }
   }

    public static class goBack implements GameCommands {
        public void exec(Player x, Scanner in, HashMap<String, GameCommands> menu, MenuTree menuTr) {
        }
    }
}
