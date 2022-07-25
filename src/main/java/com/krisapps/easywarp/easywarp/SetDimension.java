package com.krisapps.easywarp.easywarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SetDimension implements CommandExecutor {

    EasyWarp main;
    public SetDimension(EasyWarp main){
        this.main = main;
    }

    enum Dimension {
        NETHER,
        NORMAL,
        THE_END
    }


    String updateDimension(String warp, EasyWarp main, String dim) throws IOException {
            //Dimension is saved, change to another.
        String oldDimension = "";
            switch (dim) {
                case "overworld":
                    oldDimension = main.warps.getString("warps." + warp + ".location.dimension");
                    main.warps.set("warps." + warp + ".location.dimension", Dimension.NORMAL.toString());
                    main.warps.save(main.warpfile);
                    return "&aChanged dimension of &b" + warp + " &ato &eOverworld" + " &e[&b" + oldDimension + " &e-> &b" + main.warps.getString("warps." + warp + ".location.dimension") + "&e]";
                case "nether":
                    oldDimension = main.warps.getString("warps." + warp + ".location.dimension");
                    main.warps.set("warps." + warp + ".location.dimension", Dimension.NETHER.toString());
                    main.warps.save(main.warpfile);
                    return "&aChanged dimension of &b" + warp + " &ato &eNether" + " &e[&b" + oldDimension + " &e-> &b" + main.warps.getString("warps." + warp + ".location.dimension") + "&e]";
                case "end":
                    oldDimension = main.warps.getString("warps." + warp + ".location.dimension");
                    main.warps.set("warps." + warp + ".location.dimension", Dimension.THE_END.toString());
                    main.warps.save(main.warpfile);
                    return "&aChanged dimension of &b" + warp + " &ato &eThe End " + " &e[&b" + oldDimension + " &e-> &b" + main.warps.getString("warps." + warp + ".location.dimension") + "&e]";
                default:
                    return "&cFailed to change dimension: Invalid dimension.";
            }
    }

    String updatePersonalDimension(String warp, EasyWarp main, String dim, CommandSender sender) throws IOException {
        File warpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        //Dimension is saved, change to another.
        String oldDimension = warps.getString("warps." + warp + ".location.dimension");
        switch (dim) {
            case "overworld":
                warps.set("warps." + warp + ".location.dimension", Dimension.NORMAL.toString());
                warps.save(main.warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eOverworld &b[&e" + oldDimension + " &b-> &e" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            case "nether":
                warps.set("warps." + warp + ".location.dimension", Dimension.NETHER.toString());
                warps.save(main.warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eNether" + "&b[&e" + oldDimension + " &b-> &e" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            case "end":
                warps.set("warps." + warp + ".location.dimension", Dimension.THE_END.toString());
                warps.save(main.warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eThe End "  + "&b[&e" + oldDimension + " &b-> &e" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            default:
                return "&cFailed to change dimension: Invalid dimension.";
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2){
            try {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', updateDimension(args[0], main, args[1])));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if (args.length == 3){
            if (args[2] == "-p"){
                try {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', updatePersonalDimension(args[0], main, args[1], sender)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Invalid syntax.");
            return false;
        }
        return true;
    }
}
