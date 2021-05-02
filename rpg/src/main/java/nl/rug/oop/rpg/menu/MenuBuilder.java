package nl.rug.oop.rpg.menu;

import nl.rug.oop.rpg.game.GameCommands;
import nl.rug.oop.rpg.itemsystem.Inventory;
import nl.rug.oop.rpg.itemsystem.Item;
import nl.rug.oop.rpg.itemsystem.ItemInteraction;
import nl.rug.oop.rpg.npcsystem.NpcInteraction;
import nl.rug.oop.rpg.worldsystem.WorldInteraction;

import java.util.HashMap;

public class MenuBuilder {

    public HashMap<String, GameCommands> setMainCommands() {
        HashMap<String, GameCommands> explcomm = new HashMap<>();
        explcomm.put("a", new WorldInteraction.Roomcheck());
        explcomm.put("b", new WorldInteraction.Doorcheck());
        explcomm.put("c", new WorldInteraction.Npccheck());
        explcomm.put("d", new Item.ItemCheck());
        explcomm.put("e", new Inventory.InventoryCheck());
        return explcomm;
    }

    public HashMap<String, GameCommands> setDoorCommands() {
        HashMap<String, GameCommands> doorcomm = new HashMap<>();
        doorcomm.put("y", new WorldInteraction.enterDoor());
        doorcomm.put("Y", new WorldInteraction.enterDoor());
        doorcomm.put("back", new WorldInteraction.goBack());
        return doorcomm;
    }

    public HashMap<String, GameCommands> setNpcCommands() {
        HashMap<String, GameCommands> npccomm = new HashMap<>();
        npccomm.put("a", new NpcInteraction.Npcconv());
        npccomm.put("b", new NpcInteraction.Npctrade());
        npccomm.put("c", new NpcInteraction.Npcatt());
        npccomm.put("back", new WorldInteraction.goBack());
        return npccomm;
    }

    public  HashMap<String, GameCommands> setCombatCommands() {
        HashMap<String, GameCommands> npccomm = new HashMap<>();
        npccomm.put("a", new NpcInteraction.Npcatt.Attack());
        npccomm.put("b", new NpcInteraction.Npcatt.Defend());
        npccomm.put("c", new NpcInteraction.Npcatt.Flee());
        return npccomm;
    }

    public  HashMap<String, GameCommands> setItemCommands() {
        HashMap<String, GameCommands> itemcomm = new HashMap<>();
        itemcomm.put("a", new ItemInteraction.ItemInspect());
        itemcomm.put("b", new ItemInteraction.ItemConsume());
        itemcomm.put("c", new ItemInteraction.ItemPick());
        itemcomm.put("back", new WorldInteraction.goBack());
        return itemcomm;
    }
    public  HashMap<String, GameCommands> setInventoryCommands() {
        HashMap<String, GameCommands> inv = new HashMap<>();
        inv.put("w", new Inventory.InventoryInteract.getInvWeapons());
        inv.put("c", new Inventory.InventoryInteract.getInvConsum());
        inv.put("back", new WorldInteraction.goBack());
        return inv;
    }
    public  HashMap<String, GameCommands> setConsumInventoryCommands() {
        HashMap<String, GameCommands> invsub = new HashMap<>();
        invsub.put("a", new ItemInteraction.ItemInvConsume());
        invsub.put("b", new ItemInteraction.ItemInvRecycle());
        invsub.put("c", new ItemInteraction.ItemInvThrow());
        invsub.put("back", new WorldInteraction.goBack());
        return invsub;
    }
    public  HashMap<String, GameCommands> setWeaponInventoryCommands() {
        HashMap<String, GameCommands> invsub = new HashMap<>();
        invsub.put("a", new ItemInteraction.ItemInvEquip());
        invsub.put("b", new ItemInteraction.ItemInvRecycle());
        invsub.put("c", new ItemInteraction.ItemInvThrow());
        invsub.put("back", new WorldInteraction.goBack());
        return invsub;
    }
    public MenuTree buildMenuTree() {
        HashMap<String, GameCommands> explcomm = setMainCommands();
        HashMap<String, GameCommands> doorcomm = setDoorCommands();
        HashMap<String, GameCommands> npccomm = setNpcCommands();
        HashMap<String, GameCommands> combatcomm = setCombatCommands();
        HashMap<String, GameCommands> itemcomm = setItemCommands();
        HashMap<String, GameCommands> invcomm = setInventoryCommands();
        HashMap<String, GameCommands> invconsomm = setConsumInventoryCommands();
        HashMap<String, GameCommands> invwepcomm = setWeaponInventoryCommands();
        MenuTree rootmenu = new MenuTree(null, explcomm, null);
        MenuTree doormenu = new MenuTree(rootmenu, doorcomm, null);
        MenuTree npcmenu = new MenuTree(rootmenu, npccomm, null);
        MenuTree combatmenu = new MenuTree(npcmenu, combatcomm, null);
        MenuTree itemenu = new MenuTree(rootmenu, itemcomm, null);
        MenuTree invmenu = new MenuTree(rootmenu, invcomm,null);
        MenuTree invconsmenu = new MenuTree(invmenu, invconsomm,null);
        MenuTree invwepmenu = new MenuTree(invmenu, invwepcomm,null);
        HashMap<String, MenuTree> rootSubMenus = new HashMap<>();
        rootSubMenus.put("a", rootmenu);
        rootSubMenus.put("b", doormenu);
        rootSubMenus.put("c", npcmenu);
        rootSubMenus.put("d", itemenu);
        rootSubMenus.put("e", invmenu);
        HashMap<String, MenuTree> npcCsubMenu = new HashMap<>();
        HashMap<String,MenuTree> invSubMenu = new HashMap<>();
        invSubMenu.put("w", invconsmenu );
        invSubMenu.put("c", invwepmenu );
        invSubMenu.put("back", rootmenu );
        npcCsubMenu.put("x", combatmenu);
        npcCsubMenu.put("back", rootmenu);
        invmenu.submenus = invSubMenu;
        npcmenu.submenus = npcCsubMenu;
        MenuTree trmenu = new MenuTree(rootmenu, explcomm, rootSubMenus);
        trmenu.root.menunode = explcomm;
        return trmenu;
    }
}
