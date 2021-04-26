package nl.rug.oop.rpg;
import java.util.*;
import java.util.List;


public class Gameplay {

    public void Launch() {
        String name = "user";
        List<Item> inv = new ArrayList<>();//inventory system
        //script intro methods go here
        //intro dialogue tree (optional)
        World morph = new World();
        World map = morph.createMap();
        List<Room> listrm = new ArrayList<>(map.roomConnects.keySet());
        Player player = new InitEntity()
                .name(name)
                .hdm(100, 10, 30)
                .inv(inv)
                .loc(listrm.get(0))
                .protagonist();

        Explore(map, player, listrm);
    }

    public void Explore(World map, Player player, List<Room> listrm) {
        Scanner txtIn = new Scanner(System.in);
        UtilCommands.getUserName(player,txtIn);
        HashMap<String, Commands> menu;
        MenuTree trmenu = UtilCommands.buildMenuTree();
        Commands option;
       // HashMap<String, HashMap<String,Commands>> allMenus = UtilCommands.getAllMenus();
        EnumMap<Explrptions, String> exoptns = Explrptions.getExmn();

        String input = "y";

        System.out.println("Greetings, " + player.name + "!\n\\");
        printExpmenu(exoptns);
        menu = trmenu.root.menunode;
        while (!input.equals("exit sim")) {
            input = txtIn.nextLine();

            option = menu.get(input);
            if (option != null) {
                option.exec(player,txtIn,trmenu.submenus.get(input).menunode, trmenu);

                printExpmenu(exoptns);
            }
        }
        txtIn.close();
    }
    public void printExpmenu(EnumMap<Explrptions, String> exoptns) {
        exoptns.values().forEach(System.out::println);
    }

    public void reachSubMenu(HashMap<String, HashMap<String,Commands>> allMenus, String input) { //prob loop


    }

}





