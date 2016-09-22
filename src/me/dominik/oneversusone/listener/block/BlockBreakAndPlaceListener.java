package me.dominik.oneversusone.listener.block;

import me.dominik.oneversusone.Arena;
import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.manager.RegionManager;
import me.dominik.oneversusone.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by Dominik on 16.09.2016.
 */
public class BlockBreakAndPlaceListener implements Listener{

    @EventHandler
    public void onBreakEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        ItemStack s = null;
        if(!(player.getItemInHand() == null)){
            s = player.getItemInHand();
            if(!(s.getItemMeta() == null)){
                ItemMeta m = s.getItemMeta();
                if(!(m.getDisplayName() == null)){
                    if(ChatColor.stripColor(s.getItemMeta().getDisplayName()).equalsIgnoreCase("MARKER")){
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
        if(player.isOp()){
            return;
        } else if(OneVersusOne.getInstance().getList().containsKey(player.getUniqueId().toString())) {
            Arena arena = OneVersusOne.getInstance().getList().get(player.getUniqueId().toString()).arena;
            Location loc1 = arena.area1Block;
            Location loc2 = arena.area2Block;
            RegionManager regionManager = new RegionManager(loc1, loc2);
            Location max = regionManager.getLocationMax();
            Location min = regionManager.getLocationMin();

            Location block = e.getBlock().getLocation();

            if(block.getX() > min.getX() && block.getZ() > min.getZ() && block.getX() < max.getX() && block.getZ() < max.getZ()){

            } else {
                e.getPlayer().sendMessage(OneVersusOne.getPREFIX() + " Du darfst ausserhalb des Bereichs nichts abbauen");
                e.setCancelled(true);
                return;
            }
                return;
            } else {
                e.setCancelled(true);
                return;
            }

    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();

        if(OneVersusOne.getInstance().getList().containsKey(player.getUniqueId().toString())) {
            Arena arena = OneVersusOne.getInstance().getList().get(player.getUniqueId().toString()).arena;
            Location loc1 = arena.area1Block;
            Location loc2 = arena.area2Block;
            RegionManager regionManager = new RegionManager(loc1, loc2);
            Location max = regionManager.getLocationMax();
            Location min = regionManager.getLocationMin();

            Location block = e.getBlock().getLocation();

            System.out.println("block = " + block);
            System.out.println("max = " + max);
            System.out.println("min = " + min);

            if(block.getX() > min.getX() && block.getZ() > min.getZ() && block.getX() < max.getX() && block.getZ() < max.getZ()){

            } else {
                e.getPlayer().sendMessage(OneVersusOne.getPREFIX() + " Du darfst ausserhalb des Bereichs nicht bauen");
                e.setCancelled(true);
            }
        } else if(!player.isOp()){
            e.setCancelled(true);
        }
    }


}
