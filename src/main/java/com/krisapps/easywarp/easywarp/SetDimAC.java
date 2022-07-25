package com.krisapps.easywarp.easywarp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetDimAC implements TabCompleter {

    EasyWarp main;
    public SetDimAC(EasyWarp main){
        this.main = main;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1){
            list.clear();
            for (String warp : main.warps.getConfigurationSection("warps").getKeys(false)){
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
