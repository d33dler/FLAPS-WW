package nl.rug.oop.rpg.itemsystem;
import nl.rug.oop.rpg.worldsystem.Player;

public interface InventoryCommands {
    void inventoryCheck(Player x);
    void listInvWeaponItems(Player x);
    void listInvConsumableItems(Player x);
}
