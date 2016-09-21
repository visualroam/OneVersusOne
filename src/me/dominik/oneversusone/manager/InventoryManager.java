package me.dominik.oneversusone.manager;

import lombok.Getter;
import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.utils.ItemStackBuilder;
import me.dominik.oneversusone.utils.MyInventory;
import org.bukkit.CoalType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Dominik on 17.09.2016.
 */
public class InventoryManager {

    @Getter Inventory inv = new MyInventory("OneVersusOne", 27).addItemAtAllSlots(new ItemStackBuilder(Material.STAINED_GLASS_PANE, (byte) 15).amount(1).name("Background").build()).addItemAtSlot(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build()).addItemAtSlot(13, new ItemStackBuilder(Material.BED).amount(1/*TODO: Wie viele Spieler warten gerade.*/).name("§3Wainting Players").build()).addItemAtSlot(16, new ItemStackBuilder(Material.SKULL_ITEM, (byte)3).name("§2Deine Stats").amount(1).owner("_XD0M3_" /* TODO: Name des Spieler der das Invenar geöffnet hat */).build()).build();
    @Getter HashMap<Player, Inventory> inventoryHashMap = new HashMap<>();

    public InventoryManager(){

    }

    public void openInventory(Player player){
        if(inventoryHashMap.containsKey(player)){
            player.openInventory(new MyInventory(inventoryHashMap.get(player)).addItemAtSlot(13, new ItemStackBuilder(Material.BED).amount(OneVersusOne.getInstance().getQueueManager().queue.size()).name("§3Wainting Players").build()).build());
        } else {
            inventoryHashMap.put(player, new MyInventory("OneVersusOne", 27).addItemAtAllSlots(new ItemStackBuilder(Material.STAINED_GLASS_PANE, (byte) 15).amount(1).name("Background").build()).addItemAtSlot(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build()).addItemAtSlot(13, new ItemStackBuilder(Material.BED).amount(1/*TODO: Wie viele Spieler warten gerade.*/).name("§3Wainting Players").build()).addItemAtSlot(16, new ItemStackBuilder(Material.SKULL_ITEM, (byte)3).name("§2Deine Stats").amount(1).owner("_XD0M3_" /* TODO: Name des Spieler der das Invenar geöffnet hat */).build()).build());
            player.openInventory(new MyInventory(inventoryHashMap.get(player)).addItemAtSlot(13, new ItemStackBuilder(Material.BED).amount(OneVersusOne.getInstance().getQueueManager().queue.size()).name("§3Wainting Players").build()).addItemAtSlot(16,new ItemStackBuilder(Material.SKULL_ITEM, (byte)3).name("§2Deine Stats").amount(1).owner(player.getDisplayName()).build() ).build());
        }
    }

    public void updateInventory(Player player){
        Inventory inv = inventoryHashMap.get(player);
        System.out.println("inv = " + inv);
        System.out.println(OneVersusOne.getInstance().getQueueManager().queue.size());
        inv.setItem(13, new ItemStackBuilder(Material.BED).amount(OneVersusOne.getInstance().getQueueManager().queue.size()).name("§3Wainting Players").build());
    }

    public void openStatsInventory(Player player){
        Inventory inv = new MyInventory("§4Deine Stats", 9).addItemAtAllSlots(new ItemStackBuilder(Material.STAINED_GLASS_PANE, (byte) 15).amount(1).name("Background").build()).build();
        inv.setMaxStackSize(20000);
        inv.setItem(1, new ItemStackBuilder(Material.NETHER_STAR).name("§6Deine MMR: " + OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(player.getUniqueId().toString()).getElo()).amount(1).build());
        inv.setItem(3, new ItemStackBuilder(Material.GOLD_INGOT).name("§4WINS: " + OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(player.getUniqueId().toString()).getWins()).amount(1).build());
        inv.setItem(5, new ItemStackBuilder(Material.COAL, (byte) 1).name("§2LOSE: " + OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(player.getUniqueId().toString()).getLose()).amount(1).build());
        player.openInventory(inv);
    }


}
