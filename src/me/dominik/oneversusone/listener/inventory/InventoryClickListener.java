package me.dominik.oneversusone.listener.inventory;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Created by Dominik on 17.09.2016.
 */
public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory().getName().equalsIgnoreCase("test")){
            e.setCancelled(true);
        }
    }

}
