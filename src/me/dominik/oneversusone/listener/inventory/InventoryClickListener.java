package me.dominik.oneversusone.listener.inventory;

import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Dominik on 17.09.2016.
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null){
            return;
        }
        if(e.getInventory().getName().equalsIgnoreCase("§4Deine Stats")){
            e.setCancelled(true);
        }
        if(e.getInventory().getName().equalsIgnoreCase("OneVersusOne")){
            if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Start Queue")){
                OneVersusOne.getInstance().getQueueManager().joinQueue((Player) e.getWhoClicked());
                OneVersusOne.getInstance().getIvnManager().updateInventory((Player) e.getWhoClicked());
                Inventory inv = OneVersusOne.getInstance().getIvnManager().getInventoryHashMap().get(e.getWhoClicked());
                inv.setItem(10, new ItemStackBuilder(Material.WOOD_DOOR).amount(1).name("§4Leave Queue").build());
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3Wainting Players")){
                OneVersusOne.getInstance().getIvnManager().updateInventory((Player) e.getWhoClicked());
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§2Deine Stats")){
                OneVersusOne.getInstance().getIvnManager().openStatsInventory((Player) e.getWhoClicked());
                OneVersusOne.getInstance().getIvnManager().updateInventory((Player) e.getWhoClicked());
            } else if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Leave Queue")){
                OneVersusOne.getInstance().getQueueManager().leaveQueue((Player) e.getWhoClicked());
                OneVersusOne.getInstance().getIvnManager().updateInventory((Player) e.getWhoClicked());
                Inventory inv = OneVersusOne.getInstance().getIvnManager().getInventoryHashMap().get(e.getWhoClicked());
                inv.setItem(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build());
            }
            e.setCancelled(true);
        }
    }

}
