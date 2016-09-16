package me.dominik.oneversusone.commands;

import me.dominik.oneversusone.OneVersusOne;
import me.dominik.oneversusone.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Dominik on 16.09.2016.
 */
public class ArenaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("marker")){
                player.getInventory().addItem(new ItemStackBuilder(Material.WOOD_AXE).name("§4MARKER").amount(1).unbreakable().build());
                player.sendMessage(OneVersusOne.getPREFIX() + " U got the §4Marker");
                return true;
            } else if(args[0].equalsIgnoreCase("save")){
                try {
                    OneVersusOne.getInstance().getManager().saveArena(args[1],args[2],OneVersusOne.getInstance().getMap().get(player).get(1),OneVersusOne.getInstance().getMap().get(player).get(2));
                } catch (NullPointerException e){
                    player.sendMessage(OneVersusOne.getPREFIX() + " Einer der Beiden punkte wurde nicht gesetzt.");
                }
            }
        }
        return false;
    }
}
