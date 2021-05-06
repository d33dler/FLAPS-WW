package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.worldsystem.Player;

public class Dialogue {
    public static void Comunication() {
        Typewriter tw = new Typewriter();
        /* tw.type("Initiating porting with remote node...\n"); //move all to materials
        tw.poeticPause("Routing in progress...", 2000);
        for (int i = 0; i < 3; i++) {
            tw.poeticPause(".\n", 1000);
        }
        tw.type("Connection succesfully established\n");
    */

        tw.type("Hello, stranger. You logged yourself onto one of our 'Glove x-256' androids.\n");
        tw.poeticPause("", 1000);
        tw.type("We will test your ability to interact with a randomly generated world. \n" +
                "You will have to collect items, trade, engage enemies\n");
        tw.poeticPause("", 1000);
        tw.type("This trial is used to gather data about human behaviour on your planet QW1453aYUz , or Earth ,as you call it.\n");
        tw.poeticPause("", 1000);
        tw.type("We wish you a pleasant experience\n");
    }

    public static void promtBegin(Player player) {
        player.getTw().poeticPause("", 0);
        player.getTw().poeticPause("(y) PROCEED?\n", 0);
        player.getTw().poeticPause("(n) Exit\n", 0);
    }
}
