package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

/**
 * Created by Dominik on 16.09.2016.
 */

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();

        HashMap<Player, HashMap<Integer, Location>> map = OneVersusOne.getInstance().getMap();


        try {
            if(ChatColor.stripColor(player.getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("MARKER")){
                if(event.getAction() == Action.LEFT_CLICK_BLOCK){
                    Location loc = event.getClickedBlock().getLocation();
                    HashMap<Integer,Location> map1 = map.containsKey(player) ? map.get(player):new HashMap<Integer, Location>();
                    map1.put(1,loc);
                    map.put(player, map1);
                    player.sendMessage(OneVersusOne.getPREFIX() + " Point 1 set at " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
                } else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    Location loc = event.getClickedBlock().getLocation();
                    HashMap<Integer,Location> map1 = map.containsKey(player) ? map.get(player):new HashMap<Integer, Location>();
                    map1.put(2, loc);
                    map.put(player, map1);
                    player.sendMessage(OneVersusOne.getPREFIX() + " Point 2 set at " + loc.getX() + " " + loc.getY() + " " + loc.getZ());
                }
            }
        } catch (Exception ignored){

        }
    }

}
