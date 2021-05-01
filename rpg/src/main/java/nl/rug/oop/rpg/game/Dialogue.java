package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.Typewriter;

public class Dialogue {

    public void Comunication(){
        Typewriter tw = new Typewriter();
        tw.type("Hello, stranger. You logged yourself onto one of our 'Glove x-256' androids.\n");
        tw.poeticPause("", 1000);
        tw.type("We will test your ability to interact with a randomly generated world. \n" +
                "You will have to collect items, trade, engage enemies\n");
        tw.poeticPause("", 1000);
        tw.type("This trial is used to gather data about human behaviour on your planet QW1453aYUz , or Earth ,as you call it.\n");
        tw.poeticPause("", 1000);
        tw.type("We wish you a pleasant experience\n");
    }
}
