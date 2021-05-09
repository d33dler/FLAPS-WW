package nl.rug.oop.rpg.game;

import nl.rug.oop.rpg.Typewriter;
import nl.rug.oop.rpg.npcsystem.Entity;
import nl.rug.oop.rpg.npcsystem.NPC;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.doors.Door;

/**
 * Class holding various text outputs that communicate the user comical information or deliver necessary notifications.
 */
public class Dialogue {
    public static void Comunication() {
        Typewriter tw = new Typewriter();
        tw.type("Initiating porting with remote node...\n"); //move all to materials
        tw.poeticPause("Routing in progress...", 2000);
        for (int i = 0; i < 3; i++) {
            tw.poeticPause(".\n", 1000);
        }
        tw.type("Connection succesfully established\n");

        tw.type("Hello, stranger. You logged yourself onto one of our 'Glove x-256' androids.\n");
        tw.poeticPause("", 1000);
        tw.type("We will test your ability to interact with a randomly generated world. \n" +
                "You will have to collect items, trade, engage enemies\n");
        tw.poeticPause("", 1000);
        tw.type("This trial is used to gather data about human behaviour on your planet QW1453aYUz , or Earth ,as you call it.\n");
        tw.poeticPause("", 1000);
        tw.type("We wish you a pleasant experience\n");
    }

    public static void promtBegin(Player player) {
        player.gettW().poeticPause("", 0);
        player.gettW().poeticPause("(y) PROCEED?\n", 0);
        player.gettW().poeticPause("(n) Exit\n", 0);
    }

    public static void rabitService(Player player) {
        player.gettW().setSpeed(25);
        player.gettW().type("RàDDIAi: The turtle has reached its destination, thank you for using RàBIT©, Inc. drift dive services! \n");
        player.gettW().setSpeed(50);
        player.gettW().poeticPause("\n", 2000);
    }

    public static void rabitIntro(Player player) {
        player.gettW().type("RàDDIAi: Greetings, i'm a RàBIT Drift Dive Interface Ai. I will inform which doors you've bypassed.\n" +
                "Safe travels!");
        player.gettW().poeticPause("\n", 2000);
    }

    public static void rabitJumpNotice(Player player) {
        player.gettW().poeticPause("RàDDIAi : Warning: Initiating drift dive!\n", 1300);
    }

    public static void rabitJumpConfirm(Player player) {
        Door door = player.getUsed();
        player.gettW().type(". . . . .\nBypassed : " + door.getColor() + " " + door.getDoorType() + " gate " + door.visitCheck(door) + "\n");
    }

    public static void enemyInspect(Player player) {
        player.gettW().type("x-256 Ai: It is a hostile being. This species can inflict ca. "
                + player.getNpcFocus().getDamage() + " kinetic energy scale damage. Caution is advised.\n");
        player.gettW().type("Hostility level:");
        player.gettW().poeticPause("", 2000);
        player.gettW().type("High\n");
    }

    public static void allyInspect(Player player) {
        player.gettW().type("x-256 Ai: It is an amicable being. But if engaged in combat, they may inflict ca. "
                + player.getNpcFocus().getDamage() + " kinetic energy scale damage.\n");
        player.gettW().type("Hostility level:");
        player.gettW().poeticPause("", 2000);
        player.gettW().type("Low\n");
    }

    public static void exchangeBotInspect(Player player) {
        player.gettW().type("x-256 Ai: This is an Buying Operations & Trading (B.O.T) terminal.\n");
        player.gettW().type("x-256 Ai: Engaging this entity results in teleportation to an empty room for a period of time.\n");
        player.gettW().type("Hostility level:");
        player.gettW().poeticPause("", 2000);
        player.gettW().type(" Absent\n");
    }

    public static void settingContactNpc(Player player) {
        NPC npc = player.getNpcFocus();
        printAi(player);
        if (npc.getHealth() > 0) {
            player.gettW().type("You are approaching the entity.\n");
            player.gettW().poeticPause("", 2000);
            printAi(player);
            player.gettW().type("Contact set. The entity is now focused on your gestalt.\n");
        } else {
            player.gettW().type("Unfortunately, you terminated this entity's life functions.\n");
        }
    }

    public static void dialogueExBot(Player player) {
        player.gettW().type("ExB0T: Greetings " + player.getName() + "! I'm an exchange B.0.T. terminal.\n");
    }

    public static void dialogueTradeExBot(Player player) {
        player.gettW().type("ExB0T: I provide buying, selling and exchanging of material, digital and meta-digital goods" +
                "mostly, the latter!\n");
    }

    public static void dialogueAttackExBot(Player player) {
        player.gettW().type("ExB0T: It is unwise to engage me in combat,\n my security system teleports any aggressor to a remote prison" +
                ".\nEnjoy your break.\n");

    }

    public static void notifyFfC(Player player) {
        printAi(player);
        player.gettW().type("You are not able to flee from forced combats!\n");
    }

    public static void forcedCombat(Player player) {
        printAi(player);
        player.gettW().type("¿¿¿¿¿\n");
        printAi(player);
        player.gettW().type("¡¡¡ Entity is setting itself in combat mode ¡¡¡\n");
        player.gettW().type("░░░░▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓████████████████████████\n");
        player.gettW().type("Entering combat.\n");
    }
    public static void normalCombat(Player player) {
        player.gettW().type("\n░░░░▒▒▒▒▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓████████████████████████\n");
        player.gettW().type("¡Entering combat¡\n");
    }

    public static void printAi(Player player) {
        player.gettW().aI();
    }

    public static void introEnemy(Player player, Entity foe) {
        player.gettW().type(foe.getName() + ": I'm programmed to eliminate you. Overclocking combat system.\n Engaging... :" + player.getName() + "\n");
    }

    public static void notifyEnemyAttack(Player player, Entity foe) {
        player.gettW().type(foe.getName() + ": Retaliating attacker : " + player.getName() + "\n");
    }

    public static void notifyAttackPlayer(Player player) {
        player.gettW().poeticPause(player.getName() + " :", 800);
        player.gettW().type("Engaging enemy » " + player.getNpcFocus().getName() + "\n");
    }

    public static void notifyDefendPlayer(Player player) {
        player.gettW().poeticPause(player.getName() + " :", 800);
        player.gettW().type(" Initiating defence . \n");
    }

    public static void confirmCombatInit() {
        System.out.println(" x) Confirm attack\n(back) Return\n");
    }

    public static void dialogueAlly(Player player, NPC npc) {
        player.gettW().poeticPause(npc.getName() + " :", 800);
        player.gettW().type(" I come in peace " + player.getName()
                + "! . We can exchange tech goods, if you wish.\n");
    }

    public static void tradeAlly(Player player, NPC npc) {
        player.gettW().poeticPause(npc.getName() + " :", 800);
        player.gettW().type(" Unfortunately, the trading feature will be included in the DLC.Bye\n");
    }

    public static void introCombatAlly(Player player, Entity foe) {
        player.gettW().type(foe.getName() + ": Warning,   " + player.getName() + ". My sensors identified the model of an " +
                "attack algorithm pattern's inception.\n Please, reconfigure your system's program from combat mode to default." +
                "\nIf you engage me, I will have to initiate my self-preservation algorithm and retaliate.\n");
    }

    public static void combatLog() {
        System.out.println("¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯¯\n");
    }

    public static void notifyAllyAttack(Player player, Entity foe) {
        player.gettW().type(foe.getName() + ":Initiating protection sequence, retaliating aggressor : " +
                player.getName() + "\n");
    }

    public static void notifySuccess(Player player, NPC foe) {
        player.gettW().type(foe.getName() + " was eliminated successfully!\nProceeding further.\n");
        player.gettW().type(". . . .\n");
    }

    public static void notifyDamagePlayer(Player player) {
        player.gettW().type("┼ Sustained damage! Analyzing :  " + player.getNpcFocus().getDamage() + " K.E. damage\n\n");
    }

    public static String spL(String str, int len) {
        return String.format("%1$" + (len + 5) + "s", str);
    }

    public static void printObnoxiousCombatMenu(Player x, NPC foe) {
        System.out.println(" ¤   :::::::::::: SysHealth ::::::::::::  ¤ \n" + spL("", 8) +
                x.getName() + ": " + x.getHealth() + "   " + foe.getName() + ": " + foe.getHealth());
        System.out.println(" ¤   ::::::::::::   Damage  ::::::::::::  ¤ \n" + spL("", 8) +
                spL(String.valueOf(x.getDamage()), x.getName().length()) +
                "  " + spL(String.valueOf(foe.getDamage()), foe.getName().length()));
    }

    public static void notifyCessation(Player player, NPC foe) {
        player.gettW().type("The " + foe.getName() +
                " eliminated your android's gestalt from the test environment.\n" +
                "Your avatar archive is being returned to the nearest Polis checkpoint server.\n");
    }
}
