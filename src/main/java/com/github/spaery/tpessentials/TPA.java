package com.github.spaery.tpessentials;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPA implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 1 || args.length == 0) return false;
            Player target = Bukkit.getServer().getPlayer(args[0]);
            if(target == null) {
                sender.sendMessage("Player either does not exist or is offline.");
                return true;
            }
            new TPRequest((Player) sender, target);
            sender.sendMessage("Request sent.");
            return true;
        }
        return false;
    }
}
