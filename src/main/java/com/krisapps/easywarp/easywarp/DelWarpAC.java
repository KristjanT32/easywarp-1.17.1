package com.krisapps.easywarp.easywarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DelWarpAC implements TabCompleter {

    EasyWarp main;
    public DelWarpAC(EasyWarp main){
        this.main = main;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        File warpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        if (args.length == 2){
            completions.clear();
            if (args[0].equals("-p")){
                completions.clear();
                for (String warp: warps.getConfigurationSection("warps").getKeys(false)){
                    completions.add(warp);
                }
            }
        }else{
            completions.clear();
            for (String warp: main.warps.getConfigurationSection("warps").getKeys(false)){
                completions.add(warp);
            }
        }

        return completions;
    }
}
