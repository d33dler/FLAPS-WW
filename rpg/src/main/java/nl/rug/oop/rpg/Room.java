package nl.rug.oop.rpg;
import java.util.List;

public class Room {
    protected String color,atr;
    protected List<Door> doors;
    protected Item item;
    protected WeaponsDb weapon;
    protected boolean obj, company;

    enum days {
        first("first"),
        second("second");
        private final String s;
        days(String s) {
            this.s = s;
        }
    }
}
