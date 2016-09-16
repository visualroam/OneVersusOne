package me.dominik.oneversusone.commands;

import me.dominik.oneversusone.utils.ItemStackBuilder;
import me.dominik.oneversusone.utils.MyInventory;
import me.dominik.oneversusone.utils.NPC;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Dominik on 15.09.2016.
 */
public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){

            } else {
                if(args[0].equalsIgnoreCase("player")){
                    if(args[1].equalsIgnoreCase("add")){
                        OneVersusOne.getInstance().getPlayersIngame().add(player);
                        player.sendMessage(OneVersusOne.getPREFIX() + " Du wurdest der Liste hinzugefügt.");
                    } else if(args[1].equalsIgnoreCase("remove")){
                        OneVersusOne.getInstance().getPlayersIngame().remove(player);
                        player.sendMessage(OneVersusOne.getPREFIX() + " Du wurdest vond der Liste entfernt.");
                    }
                } else if(args[0].equalsIgnoreCase("NPC")){
                    if(args[1].equalsIgnoreCase("spawn")){

                        NPC npc = new NPC("DerKev","OneVersusOne","",new Random().nextInt(10000), player.getLocation(),Material.AIR,true);
                        OneVersusOne.getInstance().setNpc(npc);
                        new Thread(new Runnable() { public void run() { npc.spawn(); } }).start();
                    } else if(args[1].equalsIgnoreCase("remove")){
                        try{
                            NPC npc = OneVersusOne.getInstance().getNpc();
                            npc.despawn();

                        } catch (Exception e){

                        }
                    }
                } else if(args[0].equalsIgnoreCase("inventory")){
                    if(args[1].equalsIgnoreCase("addAll")){
                        Inventory inv = new MyInventory("&4TEST", 27).addItemAtAllSlots(new ItemStackBuilder(Material.STAINED_GLASS_PANE, (byte) 15).name("§3Background").amount(1).build()).build();
                        player.openInventory(inv);
                    }
                }
            }
        } else {

        }
        return true;
    }
}
