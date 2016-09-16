package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.utils.NPC;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Dominik on 16.09.2016.
 */
public class MenuInteractEvent implements Listener {

    @EventHandler
    public void onPlayerInteractNPC(NPC.PlayerInteractNPCEvent e) {
        NPC npc = e.getNpc();
        if(npc == OneVersusOne.getInstance().getNpc()){
            e.getPlayer().sendMessage("ihfdewsiughsfiusd");
        }
    }
}
