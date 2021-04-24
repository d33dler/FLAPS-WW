package nl.rug.oop.rpg;

public class Door implements Inspectable{
    protected DoorcolorsDb color;
    protected Room exit, enter;
    public Door (Prmtdoor parameters){
        this.color = parameters.color;
        this.exit = parameters.exit;
        this.enter = parameters.enter;
    }
    @Override
    public void inspect() {

    }
}
