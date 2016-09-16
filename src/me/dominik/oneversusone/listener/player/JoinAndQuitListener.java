package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.utils.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Dominik on 16.09.2016.
 */
public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        NPC.injectNetty(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        NPC.ejectNetty(e.getPlayer());
    }



}
