package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

public class InventoryInteraction extends WorldInteraction implements InventoryCommands {
    public InventoryInteraction() {
    }

    public void inventoryCheck(Player x) {
    }

    public void listInvItems(Player x) {
        Typewriter tw = new Typewriter();
        getItemType(x,tw);
        x.getInventory().getwList().keySet().forEach(System.out::println);
        tw.type("-----------------------\n");
        tw.type("(w) Interact\n(back) Return \n");
    }

    public void getItemType(Player x, Typewriter tw){
        switch (x.getSinput()){
            case "w":
                tw.type("-Weapons---------------\n");
                return;
            case "c": tw.type("-Consumables-----------\n");
                return;
            default: tw.type("-eRrooo00r-----------\n");
        }
    }


}
