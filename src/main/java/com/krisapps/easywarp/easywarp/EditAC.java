package com.krisapps.easywarp.easywarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EditAC implements TabCompleter {

    EasyWarp main;
    public EditAC(EasyWarp main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        //Syntax: /editpw <id> <param> <value>
        List<String> completions = new ArrayList<>();
        if (args.length == 1){
            completions.clear();
            for (String warp: main.warps.getConfigurationSection("warps").getKeys(false)){
                completions.add(warp);
            }
        }else if (args.length == 2){
            completions.clear();
            completions.add("display_name");
            completions.add("location.x");
            completions.add("location.y");
            completions.add("location.z");
            completions.add("position");
        }else if (args.length == 3 && args[2].equals("location.dimension")){
            completions.clear();
            completions.add("THE_END");
            completions.add("NORMAL");
            completions.add("NETHER");
        }

        return completions;
    }
}
