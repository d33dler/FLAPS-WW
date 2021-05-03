package nl.rug.oop.rpg.itemsystem;

import nl.rug.oop.rpg.worldsystem.Player;

public interface ItemCommands {
    void inspectItem(Player x);
    void pickItem(Player x);
    void consumeItem(Player x);
    void purgeItem(Player x);
    void consumeInvItem(Player x);
    void equipInvItem(Player x);
    void recycleItem(Player x);
}
