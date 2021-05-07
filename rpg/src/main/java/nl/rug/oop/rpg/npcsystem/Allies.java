package nl.rug.oop.rpg.npcsystem;

import nl.rug.oop.rpg.Randomizers;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Allies subtype of NPCs implementing distinct behaviour on top of NPC default behaviour.
 */

public class Allies extends NPC implements Interactable, Inspectable,NpcTypology, Attackable {
    private static final long serialVersionUID = 312L;

    public Allies(EntityBuilder parameters) {
        super(parameters);
    }
    public Allies(){
        this.probability = 40;
    }

    /**
     *
     * @return A random Ally NPC with the builder pattern implementing class
     */
    @Override
    public Allies initConstructor(){
        AlliesDatabase allyDatabase = Randomizers.randomMaterial(AlliesDatabase.class);
        return new EntityBuilder()
                .name(allyDatabase.getSpname())
                .hdm(allyDatabase.getHealth(),
                        allyDatabase.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                30), ThreadLocalRandom.current().nextInt(0, 25))
                .loc(null)
                .inv(new Inventory().generateInv())
                .ith(null)
                .createFriend();
    }

    /**
     *
     * @param player Object is used to identify the NPC object to be inspected.
     *               Player contains NPC field for setting the NPC that is currently interacting with
     *               This method overrides the default dialogue.
     */
    @Override
    public void inspect(Player player) {
        super.inspect(player);
        Dialogue.allyInspect(player);
    }

    /**
     *
     * @param player Parent class NPC interact function to execute common interaction
     *               This method overrides the default behaviour, by offering different
     *               interaction variants for the child class Ally.
     */
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
        Dialogue.introCombatAlly(player, player.getNpcFocus());
        player.setmTree(player.getmTree().getSubmenus().get(player.getSinput()));
        Combat initFight = new Combat();
        initFight.duelProcess(player, player.npccontact, player.getmTree());
    }

    public void dialogueResponse(Player player) {
        Dialogue.dialogueAlly(player, player.getNpcFocus());
    }

    public void tradeResponse(Player player) {
        Dialogue.tradeAlly(player, player.getNpcFocus());
    }

    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        super.receiveAttack(attacker, victim);
        Dialogue.notifyAllyAttack((Player) attacker, victim);
    }
}
