package com.krisapps.easywarp.easywarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditPWAC implements TabCompleter {

    EasyWarp main;
    public EditPWAC(EasyWarp main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        //Syntax: /editpw <id> <param> <value>
        List<String> completions = new ArrayList<>();
        File warpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        if (args.length == 1){
            completions.clear();
            for (String warp: warps.getConfigurationSection("warps").getKeys(false)){
                completions.add(warp);
            }
        }else if (args.length == 2){
            completions.clear();
            completions.add("display_name");
            completions.add("location.x");
            completions.add("location.y");
            completions.add("location.z");
            completions.add("location.dimension");
            completions.add("position");
        }

        return completions;
    }
}
