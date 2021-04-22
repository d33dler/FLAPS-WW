package nl.rug.oop.introduction;

public class Animal {
    private String name;
    private int energy;

    public Animal(String name, int energy) {
        this.name = name;
        this.energy = energy;
    }
    public Animal(String name) {
        this(name,33);
    }

    public void run() {
        if (energy > 0 ) {
            System.out.println(name+ ": running in c");
            energy--;
        } else {
            System.out.println(name +": is out of energy");
        }
    }

    public void eating() {
        System.out.println(name + ": Eating");
        energy++;
    }
    public void printHungerlvl() {
        System.out.println(name + " energy level: " + energy);
    }
}