package me.dominik.oneversusone.manager;

import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.OneVsOneHandler;
import me.dominik.oneversusone.utils.Countdown;
import me.dominik.oneversusone.utils.CountdownEvent;
import me.dominik.oneversusone.utils.ItemStackBuilder;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dominik on 18.09.2016.
 */
public class QueueManager {

    public ArrayList<Player> queue = new ArrayList<>();
    public HashMap<Player, Boolean> accepted = new HashMap<>();
    public HashMap<Player, Player> chat = new HashMap<>();

    public QueueManager(){

    }

    public void joinQueue(Player player){
        if(queue.contains(player)){
            return;
        }
        queue.add(player);
        if(queue.size() >= 2){

        }
        player.sendMessage(OneVersusOne.getPREFIX() + " Du bist der Queue gejoined.");

        startOneVersusOne();
    }


    public void leaveQueue(Player player){
        if(queue.contains(player)){
            queue.remove(player);
            player.sendMessage(OneVersusOne.getPREFIX() + " Du wurdest aus der Queue enfernt");
        } else {
            return;
        }

        startOneVersusOne();
    }

    public void startOneVersusOne(){
        if(queue.size() < 2) return;

        Player p1 = queue.get(0);
        Player p2 = queue.get(1);

        chat.put(p1,p2);
        accepted.put(p1,false);
        accepted.put(p2, false);

        queue.remove(p1);
        queue.remove(p2);


        p1.spigot().sendMessage(new ComponentBuilder(OneVersusOne.getPREFIX() + "Hallo, Sie haben einen Gegner, bitte ").append("akzeptieren ").bold(true).color(net.md_5.bungee.api.ChatColor.DARK_RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue accept")).append("§e sie.").create());
        p2.spigot().sendMessage(new ComponentBuilder(OneVersusOne.getPREFIX() + "Hallo, Sie haben einen Gegner, bitte ").append("akzeptieren ").bold(true).color(net.md_5.bungee.api.ChatColor.DARK_RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/queue accept")).append("§e sie.").create());

        Countdown countdown = new Countdown(OneVersusOne.getInstance(), new CountdownEvent() {
            @Override
            public void tick(int i) {

            }

            @Override
            public void finish() {
                if(accepted.get(p1)){
                    if(accepted.get(p2)){
                        OneVsOneHandler o = new OneVsOneHandler(p1, p2);
                        OneVersusOne.getInstance().getList().put(p1.getUniqueId().toString(), o);
                        OneVersusOne.getInstance().getList().put(p2.getUniqueId().toString(), o);

                        InventoryManager invM = OneVersusOne.getInstance().getIvnManager();
                        invM.getInventoryHashMap().get(p1).setItem(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build());
                        invM.getInventoryHashMap().get(p2).setItem(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build());
                        accepted.remove(p1);
                        accepted.remove(p2);
                        chat.remove(p1);

                        o.startOneVersusOne();
                        return;
                    }
                }
                InventoryManager invM = OneVersusOne.getInstance().getIvnManager();
                invM.getInventoryHashMap().get(p1).setItem(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build());
                invM.getInventoryHashMap().get(p2).setItem(10, new ItemStackBuilder(Material.DIAMOND_SWORD).amount(1).name("§6Start Queue").build());
                accepted.remove(p1);
                accepted.remove(p2);
                chat.remove(p1);
                p1.sendMessage(OneVersusOne.getPREFIX() + " Da einer der Beiden nicht angenommen hat wurden beide aus der Queue entfernt.");
                p2.sendMessage(OneVersusOne.getPREFIX() + " Da einer der Beiden nicht angenommen hat wurden beide aus der Queue entfernt.");
            }
        },10,0,20L);

        countdown.startCountdown();
    }






}
/*  OneVsOneHandler o = new OneVsOneHandler(p1, p2);
OneVersusOne.getInstance().getList().put(p1.getUniqueId().toString(), o);
        OneVersusOne.getInstance().getList().put(p2.getUniqueId().toString(), o);

        o.startOneVersusOne(); */