package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

public class InventoryInteraction extends WorldInteraction implements InventoryCommands {
    public InventoryInteraction() {
    }

    public void inventoryCheck(Player x) {
    }

    public void listInvWeaponItems(Player x) {
        getItemType(x, x.gettW());
        x.getInventory().getwList().keySet().forEach(System.out::println);
        x.gettW().type("-----------------------\n");
        x.gettW().type("(w) Interact\n(back) Return \n");
    }

    public void listInvConsumableItems(Player x) {
        getItemType(x, x.gettW());
        x.getInventory().getcList().keySet().forEach(System.out::println);
        x.gettW().type("-----------------------\n");
        x.gettW().type("(w) Interact\n(back) Return \n");
    }

    public void getItemType(Player x, Typewriter tw) {
        switch (x.getStrIn()) {
            case "w":
                tw.type("-Weapons---------------\n");
                return;
            case "c":
                tw.type("-Consumables-----------\n");
                return;
            default:
                tw.type("-eRrooo00r-----------\n");
        }
    }


}
