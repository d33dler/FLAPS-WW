package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.CombatOptions;
import nl.rug.oop.rpg.itemsystem.*;
import nl.rug.oop.rpg.npcsystem.NpcInteraction;
import nl.rug.oop.rpg.worldsystem.ExploreOptions;
import nl.rug.oop.rpg.worldsystem.Player;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;
import nl.rug.oop.rpg.npcsystem.NpcOptions;

import java.lang.reflect.*;
import java.util.HashMap;


public class MenuBuilder {
    NpcInteraction action = new NpcInteraction();
    WorldInteraction worldInter = new WorldInteraction();
    ItemInteraction itemInter = new ItemInteraction();
    InventoryInteraction invInter = new InventoryInteraction();
    GameMenu gmenu  = new GameMenu();
    public MenuTree setMainCommands() throws NoSuchMethodException {
        HashMap<String, Method> explcomm = new HashMap<>();
        explcomm.put("a", worldInter.getClass().getMethod("roomCheck", Player.class));
        explcomm.put("b", worldInter.getClass().getMethod("doorCheck", Player.class));
        explcomm.put("c", worldInter.getClass().getMethod("npcCheck", Player.class));
        explcomm.put("d", itemInter.getClass().getMethod("itemCheck", Player.class));
        explcomm.put("e", invInter.getClass().getMethod("inventoryCheck", Player.class));
        explcomm.put("f", gmenu.getClass().getMethod("switchSaveMenu", Player.class));
        MenuTree rootmenu = new MenuNodeBuilder().root(null).mNode(explcomm).subM(new HashMap<>()).
                optL(ExploreOptions.getExmn()).buildtree();
        rootmenu.submenus.put("a", rootmenu);
        return rootmenu;
    }

    public MenuTree setDoorCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setMainCommands();
        HashMap<String, Method> doorcomm = new HashMap<>();
        doorcomm.put("y", worldInter.getClass().getMethod("enterDoor", Player.class));
        doorcomm.put("Y", worldInter.getClass().getMethod("enterDoor", Player.class));
        doorcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree doormenu = new MenuNodeBuilder().root(rootmenu).mNode(doorcomm).subM(new HashMap<>()).
                optL(null).buildtree();
        rootmenu.submenus.put("b", doormenu);
        return rootmenu;
    }

    public MenuTree setNpcCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setDoorCommands();
        HashMap<String, Method> npccomm = new HashMap<>();
        npccomm.put("a", action.getClass().getMethod("conversePlayer", Player.class));
        npccomm.put("b", action.getClass().getMethod("tradePlayer", Player.class));
        npccomm.put("c", action.getClass().getMethod("engageNpc", Player.class));
        npccomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree npcmenu = new MenuNodeBuilder().root(rootmenu).mNode(npccomm).subM(new HashMap<>()).
                optL(NpcOptions.getCompany()).buildtree();
        rootmenu.submenus.put("c", npcmenu);
        return rootmenu;
    }

    public MenuTree setCombatCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setNpcCommands();
        HashMap<String, Method> combatcomm = new HashMap<>();
        combatcomm.put("a", action.getClass().getMethod("attackPlayer", Player.class));
        combatcomm.put("b", action.getClass().getMethod("defendPlayer", Player.class));
        combatcomm.put("c", action.getClass().getMethod("fleePlayer", Player.class));
        MenuTree combatmenu = new MenuNodeBuilder().root(rootmenu.submenus.get("c")).mNode(combatcomm).
                subM(new HashMap<>()).optL(CombatOptions.setMoves()).buildtree();
        rootmenu.submenus.get("c").submenus.put("x", combatmenu);
        return rootmenu;
    }

    public MenuTree setItemCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setCombatCommands();
        HashMap<String, Method> itemcomm = new HashMap<>();
        itemcomm.put("a", itemInter.getClass().getMethod("inspectItem", Player.class));
        itemcomm.put("b", itemInter.getClass().getMethod("consumeItem", Player.class));
        itemcomm.put("c", itemInter.getClass().getMethod("pickItem", Player.class));
        itemcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree itemenu = new MenuNodeBuilder().root(rootmenu).mNode(itemcomm).subM(null).
                optL(ItemOptions.getItem()).buildtree();
        rootmenu.submenus.put("d", itemenu);
        return rootmenu;
    }

    public MenuTree setInventoryCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setItemCommands();
        HashMap<String, Method> invcomm = new HashMap<>();
        invcomm.put("w", invInter.getClass().getMethod("listInvItems", Player.class));
        invcomm.put("c", invInter.getClass().getMethod("listInvItems", Player.class));
        invcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree invmenu = new MenuNodeBuilder().root(rootmenu).mNode(invcomm).subM(new HashMap<>()).
                optL(InventoryOpt.getInv()).buildtree();
        rootmenu.submenus.put("e", invmenu);
        return rootmenu;
    }

    public MenuTree setConsumInventoryCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setInventoryCommands();
        HashMap<String, Method> invsub = new HashMap<>();
        invsub.put("a", itemInter.getClass().getMethod("consumeInvItem", Player.class));
        invsub.put("b", itemInter.getClass().getMethod("recycleItem", Player.class));
        invsub.put("c", itemInter.getClass().getMethod("purgeItem", Player.class));
        invsub.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree invconsmenu = new MenuNodeBuilder().root(rootmenu.submenus.get("e")).mNode(invsub).subM(new HashMap<>()).
                optL(InvConsumOptions.getSubInv()).buildtree();
        rootmenu.submenus.get("e").submenus.put("c", invconsmenu);
        return rootmenu;
    }

    public MenuTree setWeaponInventoryCommands() throws NoSuchMethodException {
        MenuTree rootmenu = setConsumInventoryCommands();
        HashMap<String, Method> invsub = new HashMap<>();
        invsub.put("a", itemInter.getClass().getMethod("equipInvItem", Player.class));
        invsub.put("b", itemInter.getClass().getMethod("recycleItem", Player.class));
        invsub.put("c", itemInter.getClass().getMethod("purgeItem", Player.class));
        invsub.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        MenuTree invwepmenu = new MenuNodeBuilder().root(rootmenu.submenus.get("e")).mNode(invsub).subM(new HashMap<>()).
                optL(InvWeaponOptions.getSubInv()).buildtree();
        rootmenu.submenus.get("e").submenus.put("w", invwepmenu);
        return rootmenu;
    }

 /*   public HashMap<String, Method> setReturnCommand() throws NoSuchMethodException {
        HashMap<String, Method> retcomm = setConsumInventoryCommands();
        retcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return retcomm;
    }
*/
    public MenuTree buildGameMenu() throws NoSuchMethodException {
        MenuTree rootmenu = setWeaponInventoryCommands();
        MenuTree sMenu = new SaveMenuBuilder().setMainCommands();
        sMenu.setRoot(rootmenu);
        rootmenu.getSubmenus().put("f",sMenu);
        return rootmenu;
    }
}
