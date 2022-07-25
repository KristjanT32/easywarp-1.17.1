package com.krisapps.easywarp.easywarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SetPersonalDimension implements CommandExecutor {

    EasyWarp main;

    public SetPersonalDimension(EasyWarp main) {
        this.main = main;
    }

    String updatePersonalDimension(String warp, EasyWarp main, String dim, CommandSender sender) throws IOException {
        File warpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        //Dimension is saved, change to another.
        String oldDimension = "";
        switch (dim) {
            case "overworld":
                oldDimension = warps.getString("warps." + warp + ".location.dimension");
                warps.set("warps." + warp + ".location.dimension", Dimension.NORMAL.toString());
                warps.save(warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eOverworld" + " &e[&b" + oldDimension + " &e-> &b" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            case "nether":
                oldDimension = warps.getString("warps." + warp + ".location.dimension");
                warps.set("warps." + warp + ".location.dimension", Dimension.NETHER.toString());
                warps.save(warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eNether" + " &e[&b" + oldDimension + " &e-> &b" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            case "end":
                oldDimension = warps.getString("warps." + warp + ".location.dimension");
                warps.set("warps." + warp + ".location.dimension", Dimension.THE_END.toString());
                warps.save(warpfile);
                return "&aChanged dimension of &b" + warp + " &ato &eThe End " + " &e[&b" + oldDimension + " &e-> &b" + warps.getString("warps." + warp + ".location.dimension") + "&e]";
            default:
                return "&cFailed to change dimension: Invalid dimension.";
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            try {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', updatePersonalDimension(args[0], main, args[1], sender)));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            sender.sendMessage(ChatColor.RED + "Invalid syntax.");
            return false;
        }
        return true;
    }

    enum Dimension {
        NETHER,
        NORMAL,
        THE_END
    }
}
