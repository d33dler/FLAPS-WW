package nl.rug.oop.rpg.worldsystem.doors;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;
import java.io.Serializable;
import java.util.regex.Pattern;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class Door implements Inspectable, Interactable, DoorTypology, Serializable {
    private static final long serialVersionUID = 99L;
    protected String color;
    protected Room exit, enter;
    protected String doorType;
    protected int probab;
    protected int cost;
    protected boolean open, visited;
    static final Pattern vowel_check = Pattern.compile("^[aeiouy]", Pattern.CASE_INSENSITIVE);

    public Door(DoorBuilder parameters) {
        this.color = parameters.color;
        this.exit = parameters.exit;
        this.enter = parameters.enter;
        this.visited = parameters.visited;
        this.open = parameters.open;
        this.doorType = "Generic";
    }
    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr.getCname())
                .open(true)
                .vis(false)
                .create();
    }

    public Door() {
        this.probab = 70;
    }

    public void inspect(Player r) {
        int i = r.getIntIn();
        Door insD = r.getDoorFocus();
        String a = vowelCheck(insD);
        String b = visitCheck(insD);
        r.gettW().type("» (" + (i + 1) + ")" + a + insD.getColor() + " " + insD.doorType + " portal " + b + "\n");
    }

    @Override
    public void interact(Player x) {
        int door = x.getIntIn();
        Room check;
        Room room = x.getLocation();
        x.getDoorFocus().setVisited(true);
        x.setUsed(x.getDoorFocus());
        check = room.getDoors().get(door).enter;
        if (!check.getId().equals(room.getId())) {
            room = room.getDoors().get(door).enter;
        } else {
            room = room.getDoors().get(door).exit;
        }
        x.setLocation(room);
        x.gettW().type("Entering portal...\n\n");
    }



    public void notifyHalt(Player x) {
        x.gettW().poeticPause("Unexpected halt \n", 1000);
        x.gettW().type(". . .\n" + "0 compatible portal links found\nAborting RaBIT current\n");
    }

    public String vowelCheck(Door insD) {
        String a = " A ";
        if (vowel_check.matcher(insD.getColor()).find()) {
            a = " An ";
        }
        return a;
    }

    public String visitCheck(Door insD) {
        String b = "";
        if (insD.visited) {
            b = " (×) ";
        }
        return b;
    }

    public Room getExit() {
        return exit;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getColor() {
        return color;
    }

    public int getCost() {
        return cost;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getDoorType() {
        return doorType;
    }


    public void setCost(int cost) {
        this.cost = cost;
    }
}
