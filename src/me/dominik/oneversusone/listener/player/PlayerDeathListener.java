package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.OneVsOneHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Dominik on 18.09.2016.
 */
public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(OneVersusOne.getInstance().getPlayersIngame().contains(e.getEntity())){
            if(OneVersusOne.getInstance().getPlayersIngame().contains(e.getEntity().getKiller())){
                Player death = e.getEntity();
                Player killer = e.getEntity().getKiller();
                if(OneVersusOne.getInstance().getList().containsKey(killer.getUniqueId().toString())){
                    OneVsOneHandler o = OneVersusOne.getInstance().getList().get(killer.getUniqueId().toString());
                    o.finish(killer,death);
                }

            }
        }
    }

}
