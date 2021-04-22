package nl.rug.oop.introduction;

public class Main {
    protected static Animal[] initAnimals() {
        Animal porcupine = new Animal("Porcupine", 4);
        Animal turtle = new Animal("Turtle", 3);
        Animal walrus = new Animal("Walrus", 100);
        return new Animal[]{porcupine,turtle,walrus};
    }

    protected static Animal[] initAnimals(String[] args) {
        Animal[] animals = new Animal[args.length];

        for (int i  = 0; i < args.length; i++) {
            String name = args[i];
            animals[i] = new Animal(name);
        }
        return animals;
    }
    public static void main(String[] args) {
        Input Zoo = new Input();
        Zoo.Reading(args);
    }
}