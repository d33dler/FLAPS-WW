package nl.rug.oop.rpg;

public class Typewriter {
    public void type(String input) {
        for (int i = 0; i < input.length(); i++) {
            System.out.printf("%c", input.charAt(i));
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void poeticPause(String verse, int pause) {
        System.out.println(verse);
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

