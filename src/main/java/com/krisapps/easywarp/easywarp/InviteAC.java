package com.krisapps.easywarp.easywarp;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InviteAC implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        File warpFile = new File("plugins/EasyWarp/" + sender.getName() + ".yml");
        FileConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            completions.clear();
            completions.add("create");
            completions.add("list");
        }else if (args.length == 2){
            completions.clear();
            for (String warp: warps.getConfigurationSection("warps").getKeys(false)){
                completions.add(warp);
            }
        }else if (args.length == 3){
            completions.clear();
            for (Player p: Bukkit.getOnlinePlayers()){
                completions.add(p.getName());
            }
        }

        return completions;
    }
}
