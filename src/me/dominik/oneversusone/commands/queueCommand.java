package me.dominik.oneversusone.commands;

import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Dominik on 20.09.2016.
 */
public class queueCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("accept")){
                if(!OneVersusOne.getInstance().getQueueManager().accepted.get(player)){
                    OneVersusOne.getInstance().getQueueManager().accepted.put(player, true);
                    player.sendMessage(OneVersusOne.getPREFIX() + " Du hast Akzeptiert.");
                }
            } else if(args[0].equalsIgnoreCase("leave")){

            }
        }

        return true;
    }
}
