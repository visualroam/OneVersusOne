package me.dominik.oneversusone.commands;

import me.dominik.oneversusone.NPC;
import me.dominik.oneversusone.OneVersusOne;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                } else if(args[0].equals("NPC")){
                    if(args[1].equalsIgnoreCase("spawn")){
                        OneVersusOne.getInstance().setNpc(new NPC(player.getLocation()));
                        try {
                            OneVersusOne.getInstance().getNpc().spawn();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        player.sendMessage(OneVersusOne.getPREFIX() + " NPC erfolgreich gespawnt");

                    } else if(args[1].equalsIgnoreCase("remove")){
                        try{
                            NPC npc = OneVersusOne.getInstance().getNpc();
                            npc.destroy();
                        } catch (Exception e){
                            player.sendMessage("ujdsgfhvu");
                        }
                    }
                }
            }
        } else {

        }
        return true;
    }
}
