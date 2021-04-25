package nl.rug.oop.rpg;

import java.util.Scanner;

public class Mainmenu {

    public void poeticPause(String verse, int pause) {
        System.out.println(verse);
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void initMenu() {
       /* poeticPause("Initiating porting with remote node...", 100); //move all to materials
        poeticPause("Routing in progress...", 2000);
        for (int i = 0; i < 3; i++) {
            poeticPause(".", 1000);
        }
        */ //unlock later

        poeticPause("Connection succesfully established", 1000);
        poeticPause("",0);
        poeticPause("(y) PROCEED?",0);
        poeticPause("(n) Exit",0);
        Scanner rdtxt = new Scanner(System.in);
        if(rdtxt.nextLine().equals("y")) {
            Gameplay start = new Gameplay();
            start.Launch();
            rdtxt.close();
        } else {
            rdtxt.close();
            System.exit(0);
        }

    }
}
