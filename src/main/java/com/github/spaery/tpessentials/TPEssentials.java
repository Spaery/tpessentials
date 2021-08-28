package com.github.spaery.tpessentials;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TPEssentials extends JavaPlugin
{
    private static TPEssentials plugin;
    private FileConfiguration config = this.getConfig();
    private HashMap<TPRequest,SimpleEntry<Player,Player>> requestArray = new HashMap<>();
    @Override
    public void onEnable(){
        plugin = this;
        this.saveDefaultConfig();
        this.getCommand("tpa").setExecutor(new TPA());
        this.getCommand("tpaccept").setExecutor(new TPAccept());
        this.getCommand("tpdeny").setExecutor(new TPDeny());
    }

    @Override
    public void onDisable(){
    }

    public FileConfiguration getDefaultConfig(){
        return config;
    }

    public static TPEssentials getPlugin(){
        return plugin;
    }

    public TPRequest getRequestBySender(Player sender){
        for(Entry<TPRequest,SimpleEntry<Player,Player>> entry: requestArray.entrySet()){
            if(entry.getValue().getKey().equals(sender)){
                return (TPRequest)entry.getKey();
            }
        }
        return null;
    }

    public TPRequest getRequestByTarget(Player target){
        for(Entry<TPRequest,SimpleEntry<Player,Player>> entry: requestArray.entrySet()){
            if(entry.getValue().getValue().equals(target)){
                return (TPRequest)entry.getKey();
            }
        }
        return null;
    }

    public void registerRequest(TPRequest reqToAdd,Player sender, Player target){
        SimpleEntry<Player,Player> senderTargetPair = new SimpleEntry<Player,Player>(sender, target);
        requestArray.put(reqToAdd,senderTargetPair);
    }

    public void removeRequest(TPRequest req){
        requestArray.remove(req);
    }

    public boolean requestExist(TPRequest req){
        return requestArray.containsKey(req);
    }

}