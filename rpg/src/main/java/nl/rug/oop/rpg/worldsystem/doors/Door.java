package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.Room;

import static nl.rug.oop.rpg.Randomizers.randomMaterial;

public class Door implements Inspectable, Interactable,DoorClass {
    protected DoorcolorsDB color;
    protected Room exit, enter;
    protected String dtype;
    protected int probab;
    protected int cost;
    protected boolean open;

    public Door(DoorBuilder parameters) {
        this.color = parameters.color;
        this.exit = parameters.exit;
        this.enter = parameters.enter;
        this.dtype = "Generic";
    }

    public Door() {
        this.probab = 70;
    }

    public void inspect(Player r) {
        int i = r.getIntin();
        Door insD = r.getFocus();
        r.getTw().type("╠ < "+ (i + 1)+ ">A " + insD.dtype  + "portal which is " + insD.color +"\n");
    }

    @Override
    public void interact(Player ava) {
        int x = ava.getIntin();
        Room check;
        Room room = ava.getLocation();
        check = room.getDoors().get(x).enter;
        if (!check.getId().equals(room.getId())) {
            room = room.getDoors().get(x).enter;
        } else {
            room = room.getDoors().get(x).exit;
        }
        ava.setUsed(room.getDoors().get(x));
        ava.setLocation(room);
        ava.getTw().type("Entering portal...\n\n");
    }

    public Room getExit() {
        return exit;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Room getEnter() {
        return enter;
    }

    public DoorcolorsDB getColor() {
        return color;
    }

    public void setEnter(Room enter) {
        this.enter = enter;
    }
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getProbab() {
        return probab;
    }

    public Door initConstructor(Room out, Room goin) {
        DoorcolorsDB clr = randomMaterial(DoorcolorsDB.class);
        return new DoorBuilder()
                .exit(out)
                .enter(goin)
                .clr(clr)
                .create();
    }
    public void setProbab(int probab) {
        this.probab = probab;
    }

    public void notifyHalt(Player x){
        x.getTw().poeticPause("Unexpected halt \n",1000);
        x.getTw().type(". . .\n" + "0 compatible portal links found\nAborting RaBIT current\n");
    }
}
