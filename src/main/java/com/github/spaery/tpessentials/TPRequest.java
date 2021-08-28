package com.github.spaery.tpessentials;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TPRequest {
    private TPEssentials plugin = TPEssentials.getPlugin();
    private FileConfiguration config = plugin.getDefaultConfig();
    private Player sender;
    private Player target;

    public TPRequest(Player sender, Player target){
        this.sender = sender;
        this.target = target;
        plugin.registerRequest(this,sender,target);
        target.sendMessage(sender.getName() + " has sent a tpa request to you. Accept with /tpaccept");
        long duration = config.getLong("RequestDuration");
        timer(duration, sender, target, this);
    }

    private void timer(long t, Player sender, Player target, TPRequest req){
        Bukkit.getScheduler().runTaskLater(TPEssentials.getPlugin(), new Runnable(){
            @Override
            public void run(){
                if(plugin.requestExist(req)){
                    sender.sendMessage("Your request has timed out.");
                    plugin.removeRequest(plugin.getRequestByTarget(target));
                }
            }
        }, 20L*t);
    }

    public Player getSender(){
        return sender;
    }
    public Player getTarget(){
        return target;
    }
    public void cancel(){
        plugin.removeRequest(this);
    }
}
