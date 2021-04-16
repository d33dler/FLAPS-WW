package nl.rug.oop.introduction;

import java.util.Scanner;

public class Input {
    public void Reading (String[] args) {
        Scanner rdtxt = new Scanner(System.in);
        Animal[] animals;
        if (args.length == 0 ) {
            animals = Main.initAnimals();
        } else {
            animals = Main.initAnimals(args);
        }
        while (rdtxt.hasNextLine()) {
            String input = rdtxt.nextLine();
            for (Animal animal: animals) {
                if (input.equals("eat")) {
                    animal.eating();
                } else if (input.equals("run")) {
                    animal.run();
                }
                animal.printHungerlvl();
            }
        }
        rdtxt.close();
    }
}