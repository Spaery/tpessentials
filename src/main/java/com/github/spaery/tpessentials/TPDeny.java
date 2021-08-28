package com.github.spaery.tpessentials;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPDeny implements CommandExecutor{
    private TPEssentials plugin = TPEssentials.getPlugin();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            if(args.length > 0) return false;
            TPRequest request = plugin.getRequestByTarget((Player) sender);
            if(request == null){
                sender.sendMessage("You don't currently have a tp request.");
                return true;
            }
            request.getSender().sendMessage("The player declined your request to teleport.");
            request.cancel();
            return true;
        }
        return false;
    }
    
}
