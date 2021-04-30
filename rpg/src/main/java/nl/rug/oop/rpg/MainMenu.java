package nl.rug.oop.rpg;

import java.util.Scanner;

public class MainMenu {



    public void initMenu() {
       /* poeticPause("Initiating porting with remote node...", 100); //move all to materials
        poeticPause("Routing in progress...", 2000);
        for (int i = 0; i < 3; i++) {
            poeticPause(".", 1000);
        }
        */ //unlock later
        Typewriter tw = new Typewriter();
      //  tw.type("Connection succesfully established");
        tw.poeticPause("",0);
        tw.poeticPause("(y) PROCEED?",0);
        tw.poeticPause("(n) Exit",0);
        Scanner rdtxt = new Scanner(System.in);
        String input = rdtxt.nextLine();
        if(input.equals("y")) {
            Gameplay start = new Gameplay();
            start.Launch(rdtxt);
        } else {
            rdtxt.close();
            System.exit(0);
        }
        rdtxt.close();
    }
}
