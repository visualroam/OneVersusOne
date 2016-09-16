package me.dominik.oneversusone.listener.entity;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Dominik on 15.09.2016.
 */
public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(OneVersusOne.getInstance().getPlayersIngame().contains((Player) e.getDamager())){
                if(OneVersusOne.getInstance().getPlayersIngame().contains((Player) e.getEntity())){
                    return;
                }
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }
    }

}
