package nl.rug.oop.rpg.npcsystem;
import nl.rug.oop.rpg.Randomizers;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.concurrent.ThreadLocalRandom;


public class ExchangeBots extends NPC implements Inspectable, Interactable,Attackable, NpcTypology {
    private static final long serialVersionUID = 333L;
    public ExchangeBots(EntityBuilder parameters) {
        super(parameters);
    }

    public ExchangeBots(){
        this.probability = 10;
    }

    @Override
    public void inspect(Player player) {
        super.inspect(player);
        Dialogue.exchangeBotInspect(player);
    }

    @Override
    public ExchangeBots initConstructor(){
        ExBots exBotsDatabase = Randomizers.randomMaterial(ExBots.class);
        return new EntityBuilder()
                .name(exBotsDatabase.getSpname())
                .hdm(exBotsDatabase.getHealth(),
                        exBotsDatabase.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                30), ThreadLocalRandom.current().nextInt(0, 25))
                .loc(null)
                .inv(new Inventory().generateInv())
                .ith(null)
                .createExBots();
    }


    @Override
    public void interact(Player player) {
        switch (player.getIntent()) {
            case "combat":
                acceptCombat(player);
                return;
            case "converse":
                dialogueResponse(player);
                return;
            case "trade":
                tradeResponse(player);
                return;
            default:
        }
    }
    public void acceptCombat(Player player) {
        Dialogue.dialogueAttackExBot(player);
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dialogueResponse(Player player) {
        Dialogue.dialogueExBot(player);
    }

    public void tradeResponse(Player player) {
        Dialogue.dialogueTradeExBot(player);
    }

    @Override
    public void receiveAttack(Entity attacker, Entity victim) {

    }
}
