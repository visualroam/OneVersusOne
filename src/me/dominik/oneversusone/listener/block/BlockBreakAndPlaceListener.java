package me.dominik.oneversusone.listener.block;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Dominik on 16.09.2016.
 */
public class BlockBreakAndPlaceListener implements Listener{

    @EventHandler
    public void onBreakEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        try {
            if(ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("MARKER")){
                e.setCancelled(true);
            } else if(player.isOp()){

            } else {
                e.setCancelled(true);
            }
        } catch (Exception ignored){

        }
    }


}
