package nl.rug.oop.rpg.worldsystem.doors;

import java.io.Serializable;

public enum DoorcolorsDB {
    red(" red"),
    blue(" blue"),
    black(" black"),
    gray(" gray"),
    cyan(" cyan");

    private final String cname;

    DoorcolorsDB(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }
}
