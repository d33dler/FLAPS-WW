package nl.rug.oop.rpg;

public class Typewriter {
    protected int speed;
    public void type(String input) {
        for (int i = 0; i < input.length(); i++) {
            System.out.printf("%c", input.charAt(i));
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public void poeticPause(String verse, int pause) {
        System.out.print(verse);
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void aI(){
        poeticPause("x-256 Ai: ", 1000);
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}

