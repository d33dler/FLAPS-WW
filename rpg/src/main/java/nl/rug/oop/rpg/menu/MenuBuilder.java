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

    public HashMap<String, Method> setMainCommands() throws NoSuchMethodException {
        HashMap<String, Method> explcomm = new HashMap<>();
        explcomm.put("a", worldInter.getClass().getMethod("roomCheck", Player.class));
        explcomm.put("b", worldInter.getClass().getMethod("doorCheck", Player.class));
        explcomm.put("c", worldInter.getClass().getMethod("npcCheck", Player.class));
        explcomm.put("d", itemInter.getClass().getMethod("itemCheck", Player.class));
        explcomm.put("e", invInter.getClass().getMethod("inventoryCheck", Player.class));
        return explcomm;
    }

    public HashMap<String, Method> setDoorCommands() throws NoSuchMethodException {
        HashMap<String, Method> doorcomm = new HashMap<>();
        doorcomm.put("y", worldInter.getClass().getMethod("enterDoor", Player.class));
        doorcomm.put("Y", worldInter.getClass().getMethod("enterDoor", Player.class));
        doorcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return doorcomm;
    }

    public HashMap<String, Method> setNpcCommands() throws NoSuchMethodException {
        HashMap<String, Method> npccomm = new HashMap<>();
        npccomm.put("a", action.getClass().getMethod("conversePlayer", Player.class));
        npccomm.put("b", action.getClass().getMethod("tradePlayer", Player.class));
        npccomm.put("c", action.getClass().getMethod("engageNpc", Player.class));
        npccomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return npccomm;
    }

    public HashMap<String, Method> setCombatCommands() throws NoSuchMethodException {
        HashMap<String, Method> npccomm = new HashMap<>();
        npccomm.put("a", action.getClass().getMethod("attackPlayer", Player.class));
        npccomm.put("b", action.getClass().getMethod("defendPlayer", Player.class));
        npccomm.put("c", action.getClass().getMethod("fleePlayer", Player.class));
        return npccomm;
    }

    public HashMap<String, Method> setItemCommands() throws NoSuchMethodException {
        HashMap<String, Method> itemcomm = new HashMap<>();
        itemcomm.put("a", itemInter.getClass().getMethod("inspectItem", Player.class));
        itemcomm.put("b", itemInter.getClass().getMethod("consumeItem", Player.class));
        itemcomm.put("c", itemInter.getClass().getMethod("pickItem", Player.class));
        itemcomm.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return itemcomm;
    }

    public HashMap<String, Method> setInventoryCommands() throws NoSuchMethodException {
        HashMap<String, Method> inv = new HashMap<>();
        inv.put("w", invInter.getClass().getMethod("getInvWeapons", Player.class));
        inv.put("c", invInter.getClass().getMethod("getInvConsum", Player.class));
        inv.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return inv;
    }

    public HashMap<String, Method> setConsumInventoryCommands() throws NoSuchMethodException {
        HashMap<String, Method> invsub = new HashMap<>();
        invsub.put("a", itemInter.getClass().getMethod("consumeInvItem", Player.class));
        invsub.put("b", itemInter.getClass().getMethod("recycleItem", Player.class));
        invsub.put("c", itemInter.getClass().getMethod("purgeItem", Player.class));
        invsub.put("back", worldInter.getClass().getMethod("goBack", Player.class));
        return invsub;
    }

    public HashMap<String, Method> setWeaponInventoryCommands() throws NoSuchMethodException {
        HashMap<String, Method> invsub = setConsumInventoryCommands();
        invsub.put("a", itemInter.getClass().getMethod("equipInvItem", Player.class));
        return invsub;
    }

    public MenuTree buildMenuTree() throws NoSuchMethodException {

        HashMap<String, Method> explcomm = setMainCommands();
        HashMap<String, Method> doorcomm = setDoorCommands();
        HashMap<String, Method> npccomm = setNpcCommands();
        HashMap<String, Method> combatcomm = setCombatCommands();
        HashMap<String, Method> itemcomm = setItemCommands();
        HashMap<String, Method> invcomm = setInventoryCommands();
        HashMap<String, Method> invconsomm = setConsumInventoryCommands();
        HashMap<String, Method> invwepcomm = setWeaponInventoryCommands();
        MenuTree rootmenu = new mNodeBuilder().root(null).mNode(explcomm).subM(null).
                optL(ExploreOptions.getExmn()).buildtree();
        MenuTree doormenu = new mNodeBuilder().root(rootmenu).mNode(doorcomm).subM(null).
                optL(null).buildtree();
        MenuTree npcmenu = new mNodeBuilder().root(rootmenu).mNode(npccomm).subM(null).
                optL(NpcOptions.getCompany()).buildtree();
        MenuTree combatmenu = new mNodeBuilder().root(npcmenu).mNode(combatcomm).subM(null).
                optL(CombatOptions.setMoves()).buildtree();
        MenuTree itemenu = new mNodeBuilder().root(rootmenu).mNode(itemcomm).subM(null).
                optL(ItemOptions.getItem()).buildtree();
        MenuTree invmenu = new mNodeBuilder().root(rootmenu).mNode(invcomm).subM(null).
                optL(InventoryOpt.getInv()).buildtree();
        MenuTree invconsmenu = new mNodeBuilder().root(invmenu).mNode(invconsomm).subM(null).
                optL(InvConsumOptions.getSubInv()).buildtree();
        MenuTree invwepmenu = new mNodeBuilder().root(invmenu).mNode(invwepcomm).subM(null).
                optL(InvWeaponOptions.getSubInv()).buildtree();
        HashMap<String, MenuTree> rootSubMenus = new HashMap<>();
        rootSubMenus.put("a", rootmenu);
        rootSubMenus.put("b", doormenu);
        rootSubMenus.put("c", npcmenu);
        rootSubMenus.put("d", itemenu);
        rootSubMenus.put("e", invmenu);
        HashMap<String, MenuTree> npcCsubMenu = new HashMap<>();
        HashMap<String, MenuTree> invSubMenu = new HashMap<>();
        invSubMenu.put("w", invconsmenu);
        invSubMenu.put("c", invwepmenu);
        invSubMenu.put("back", rootmenu);
        npcCsubMenu.put("x", combatmenu);
        npcCsubMenu.put("back", rootmenu);
        invmenu.submenus = invSubMenu;
        npcmenu.submenus = npcCsubMenu;
        MenuTree trmenu = new mNodeBuilder().root(rootmenu).mNode(explcomm).subM(rootSubMenus).
                optL(ExploreOptions.getExmn()).buildtree();
        trmenu.root.menunode = explcomm;
        return trmenu;
    }
}
