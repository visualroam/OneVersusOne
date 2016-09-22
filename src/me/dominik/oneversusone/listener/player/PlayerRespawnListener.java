package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import javax.persistence.Embeddable;

/**
 * Created by Dominik on 22.09.2016.
 */
public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void lol(PlayerRespawnEvent e){
        spawnNPC(e.getPlayer());
    }

    public void spawnNPC(Player player){
        new Thread(new Runnable() { public void run() { OneVersusOne.getInstance().getNpc().spawnOne(player); } }).start();
        Bukkit.getScheduler().scheduleSyncDelayedTask(OneVersusOne.getInstance(), new Runnable() {
            @Override
            public void run() {
                OneVersusOne.getInstance().getNpc().removeFromTablist();
            }
        },40);
    }

}
