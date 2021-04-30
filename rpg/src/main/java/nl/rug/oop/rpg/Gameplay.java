package nl.rug.oop.rpg;

import java.util.*;
import java.util.List;


public class Gameplay {

    public void Launch(Scanner input) {
        Dialogue intro = new Dialogue();
        intro.Comunication();
        World morph = new World();
        World map = morph.createMap();
        Player player = generatePlayer(map);
        Explore(player, input);
    }

    public void Explore(Player player, Scanner txtIn) {
        Utilities.getUserName(player, txtIn);
        MenuTree trmenu = Utilities.buildMenuTree();
        HashMap<String, GameCommands> menu;
        GameCommands option;
        EnumMap<Explrptions, String> exoptns = Explrptions.getExmn();
        String input = "y";
        System.out.println("Greetings, " + player.name + "!\n \n");
        printExpmenu(exoptns);
        menu = trmenu.root.menunode;
        while (!input.equals("exit sim")) {
            input = txtIn.nextLine();
            option = menu.get(input);
            if (option != null) {
                option.exec(player, txtIn, trmenu.submenus.get(input).menunode, trmenu);
                printExpmenu(exoptns);
            }
        }
        txtIn.close();
    }

    public void printExpmenu(EnumMap<Explrptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void reachSubMenu(HashMap<String, HashMap<String, GameCommands>> allMenus, String input) { //prob loop

    }

    public Player generatePlayer(World map) {
        Inventory inv = new Inventory().generateInv(); //verify integrity
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Player player = new InitEntity()
                .name("user")
                .hdm(100, 10, 30)
                .inv(inv)
                .loc(listrm.get(0))
                .fl(false)
                .ith(new InitItem().name("Raygun").dmg(14).val(10).createWep())
                .protagonist();
        Item itemc = map.generateItem("11a");
        Item itemw = map.generateItem("Xz");
        player.inventory.wList.put(itemw.getName(), (Weapons) itemw);
        player.inventory.cList.put(itemc.getName(), (Consumables) itemc);
        return player;
    }
}





