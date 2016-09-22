package me.dominik.oneversusone.commands;

import me.dominik.oneversusone.utils.ItemStackBuilder;
import me.dominik.oneversusone.utils.MyInventory;
import me.dominik.oneversusone.utils.NPC;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

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

                        NPC npc = new NPC("DerKev","§4One§6Versus§4One","",new Random().nextInt(10000), player.getLocation(),Material.AIR,true);
                        OneVersusOne.getInstance().setNpc(npc);
                        new Thread(new Runnable() { public void run() { npc.spawn(); } }).start();
                    } else if(args[1].equalsIgnoreCase("remove")){
                        try{
                            NPC npc = OneVersusOne.getInstance().getNpc();
                            npc.despawn();

                        } catch (Exception e){

                        }
                    } else if(args[1].equalsIgnoreCase("real")){
                        OneVersusOne.getInstance().getNpcManager().saveNPCLocation(player.getLocation());
                        player.sendMessage("dijsiopf");
                    }
                } else if(args[0].equalsIgnoreCase("inventory")){
                    if(args[1].equalsIgnoreCase("addAll")){
                        Inventory inv = new MyInventory("&4TEST", 27).addItemAtAllSlots(new ItemStackBuilder(Material.STAINED_GLASS_PANE, (byte) 15).name("§3Background").amount(1).build()).build();
                        player.openInventory(inv);
                    }
                } else if(args[0].equalsIgnoreCase("amount")){
                    player.sendMessage(OneVersusOne.getPREFIX() + " " + OneVersusOne.getInstance().getManager().getAmountArenas());
                }else if(args[0].equalsIgnoreCase("gson")){
                    player.sendMessage(OneVersusOne.locationGson.toJson(player.getLocation()));
                } else if(args[0].equalsIgnoreCase("items")){
                    player.getInventory().clear();

                    PlayerInventory i = player.getInventory();

                    i.setItem(0,new ItemStackBuilder(Material.DIAMOND_SWORD).name("§4§lThe Striker").lore("The Legendäre Striker,").lore("kämpfte schon mit").lore("mit diesem Schwert.").amount(1).build());
                    i.setItem(1, new ItemStackBuilder(Material.GOLDEN_APPLE, (byte) 1).amount(1).name("§4§lThe Most OP Item").build());
                    i.setItem(2, new ItemStackBuilder(Material.BOW).amount(1).name("§4§lAmor's Bow").enchantment(Enchantment.ARROW_INFINITE, 1).build());
                    i.setItem(3, new ItemStackBuilder(Material.ARROW).amount(1).name("§4§lLove Arrow").build());

                    i.setHelmet(new ItemStackBuilder(Material.DIAMOND_HELMET).build());
                    i.setChestplate(new ItemStackBuilder(Material.DIAMOND_CHESTPLATE).build());
                    i.setLeggings(new ItemStackBuilder(Material.DIAMOND_LEGGINGS).build());
                    i.setBoots(new ItemStackBuilder(Material.DIAMOND_BOOTS).build());
                }
            }
        } else {

        }
        return true;
    }
}
