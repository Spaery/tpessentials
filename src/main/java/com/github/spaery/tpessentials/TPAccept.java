package com.github.spaery.tpessentials;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TPAccept implements CommandExecutor {
    private TPEssentials plugin = TPEssentials.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player acceptingPlayer = (Player) sender;
            if(args.length > 0) return false;
            TPRequest request = plugin.getRequestByTarget((Player)sender);
            if(request == null){
                sender.sendMessage("You don't currently have a tp request.");
                return true;
            } else {
                Player requestingPlayer = request.getSender();
                request.cancel();
                Bukkit.getScheduler().runTaskTimer(TPEssentials.getPlugin(), new Consumer<BukkitTask>(){
                    int i = plugin.getConfig().getInt("TimeToWait");
                    @Override
                    public void accept(BukkitTask task) {
                        if(i <= 0){
                            requestingPlayer.teleport(acceptingPlayer.getLocation());
                            task.cancel();
                        } else{
                            requestingPlayer.sendMessage("You will be teleported in " + i);
                            requestingPlayer.playSound(requestingPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, (float) 0.5, 1);
                            i--;
                        }
                    }
                }, 0L, 20L);
                return true;
            }
        }
        return false;
    }
}
