package com.krisapps.easywarp.easywarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetPersonalDimAC implements TabCompleter {

    EasyWarp main;
    public SetPersonalDimAC(EasyWarp main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        File warpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        if (args.length == 1){
            list.clear();
            for (String warp : warps.getConfigurationSection("warps").getKeys(false)){
                list.add(warp);
            }
        }else if (args.length == 2){
            list.clear();
            list.add("overworld");
            list.add("nether");
            list.add("the_end");
        }
        return list;
    }
}
