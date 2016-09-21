package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Created by Dominik on 19.09.2016.
 */
public class FoodChangeListener implements Listener{

    @EventHandler
    public void onChange(FoodLevelChangeEvent e){
        Player player = (Player) e.getEntity();
        if(OneVersusOne.getInstance().getList().containsKey(player.getUniqueId().toString())){

        } else {
            e.setFoodLevel(20);
            e.setCancelled(true);
        }
    }

}
