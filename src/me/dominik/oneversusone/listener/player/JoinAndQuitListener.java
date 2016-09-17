package me.dominik.oneversusone.listener.player;

import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.utils.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Random;

/**
 * Created by Dominik on 16.09.2016.
 */
public class JoinAndQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        NPC.injectNetty(e.getPlayer());
        spawnNPC(e.getPlayer());
        OneVersusOne.getInstance().getPdmanager().createPlayer(e.getPlayer());
        OneVersusOne.getInstance().getUUID_GAMEPROFIL().put(e.getPlayer().getUniqueId().toString(), OneVersusOne.getInstance().getProfileManager().createProfile(e.getPlayer()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        NPC.ejectNetty(e.getPlayer());
        OneVersusOne.getInstance().getProfileManager().updateProfile(OneVersusOne.getInstance().getUUID_GAMEPROFIL().get(e.getPlayer().getUniqueId().toString()));
    }

    public void spawnNPC(Player player){
        NPC npc = new NPC("_XD0M3_","OneVersusOne","",new Random().nextInt(10000), OneVersusOne.getInstance().getNpcManager().getNPCLocation(), Material.AIR,true);
        OneVersusOne.getInstance().setNpc(npc);
        new Thread(new Runnable() { public void run() { npc.spawnOne(player); } }).start();
    }



}
