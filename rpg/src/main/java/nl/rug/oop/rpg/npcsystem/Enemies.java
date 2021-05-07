package nl.rug.oop.rpg.npcsystem;
import nl.rug.oop.rpg.Randomizers;
import nl.rug.oop.rpg.game.Combat;
import nl.rug.oop.rpg.game.Dialogue;
import nl.rug.oop.rpg.game.Inspectable;
import nl.rug.oop.rpg.game.Interactable;
import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.worldsystem.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Enemies extends NPC implements Interactable, Inspectable, Attackable, NpcTypology {
    private static final long serialVersionUID = 1203L;

    public Enemies(EntityBuilder parameters) {
        super(parameters);
    }

    public Enemies(){
        this.probability = 40;
    }

    @Override
    public Enemies initConstructor(){
        EnemyDatabase enemyDatabase = Randomizers.randomMaterial(EnemyDatabase.class);
        return new EntityBuilder()
                .name(enemyDatabase.getSpname())
                .hdm(enemyDatabase.getHealth(),
                        enemyDatabase.getDamage() + ThreadLocalRandom.current().nextInt(2,
                                10), ThreadLocalRandom.current().nextInt(0, 15))
                .loc(null)
                .inv(new Inventory().generateInv())
                .ith(null)
                .createEnemy();
    }
    
    @Override
    public void inspect(Player player) {
        super.inspect(player);
        if (player.getNpcFocus().getHealth() > 0) {
            Dialogue.enemyInspect(player);
        }
    }

    /**
     * @param player
     */

    @Override
    public void interact(Player player) {
        if (player.lifeCheck(player)) {
            return;
        }
        super.interact(player);
        player.getTw().type(player.name + ": Hello , are you friendly?\n");
        player.setmTree(player.getmTree().getSubmenus().get("x"));
        Combat initFight = new Combat();
        Dialogue.forcedCombat(player);
        Dialogue.introEnemy(player, player.getNpcFocus());
        player.setForcedcomb(true);
        initFight.duelProcess(player, player.npccontact, player.getmTree());
    }

    @Override
    public void receiveAttack(Entity attacker, Entity victim) {
        super.receiveAttack(attacker, victim);
        Dialogue.notifyEnemyAttack((Player) attacker, victim);
    }

}
