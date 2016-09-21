package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * Created by Dominik on 20.09.2016.
 */
public class PlayerDropListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){

        if(OneVersusOne.getInstance().getList().containsKey(e.getPlayer().getUniqueId().toString())){

        } else {
            e.setCancelled(true);
        }

    }

}
