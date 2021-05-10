package nl.rug.oop.rpg.worldsystem.doors;

import nl.rug.oop.rpg.worldsystem.Room;

public interface DoorTypology {
    Door initConstructor(Room A, Room B);
}
