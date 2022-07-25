package com.krisapps.easywarp.easywarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CreateWarp implements CommandExecutor {

    File warpfile;
    EasyWarp main;

    public CreateWarp(EasyWarp main, File warpfile) {
        this.main = main;
        this.warpfile = warpfile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /createwarp <id> <display_name>
        //Creates a warp with current coordinates.
        if (sender instanceof Player) {
            if (args.length > 0) {
                YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
                String warpID = args[0];
                String warp_displayname = "";
                for (int i = 1; i != args.length; i++) {
                    warp_displayname += args[i] + " ";
                }
                String x = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getX());
                String y = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getY());
                String z = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getZ());
                if (!warps.contains("warps." + args[0])) {
                    //Save warp info
                    warps.set("warps." + warpID + "." + "display_name", warp_displayname);
                    warps.set("warps." + warpID + "." + "id", warpID);
                    warps.set("warps." + warpID + "." + "location.x", x);
                    warps.set("warps." + warpID + "." + "location.y", y);
                    warps.set("warps." + warpID + "." + "location.z", z);
                    warps.set("warps." + warpID + ".location.dimension", Bukkit.getPlayer(sender.getName()).getWorld().getEnvironment().toString());
                    warps.set("warps." + warpID + ".author", sender.getName());
                    //Save dimension
                    if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NORMAL") {
                        warps.set("globals.main-world", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NETHER") {
                        warps.set("globals.nether", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "THE_END") {
                        warps.set("globals.end", ((Player) sender).getLocation().getWorld().getName());
                    }
                    try {
                        warps.save(warpfile);
                    } catch (IOException e) {
                        sender.sendMessage(ChatColor.RED + "Error saving warp information. Check the console for details.");
                        e.printStackTrace();
                    }
                    sender.sendMessage(ChatColor.GREEN + "Warp created successfully.\nYou can now warp here by /warp " + ChatColor.YELLOW + warpID);
                } else if (warps.contains("warps." + args[0])) {
                    sender.sendMessage(ChatColor.RED + "Could not finish the requested operation: The id is in use by another warp.");
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Syntax error: Too few arguments provided.\nNeeded at least: 2\nProvided: " + args.length);
                return false;
            }
        } else {
            sender.sendMessage(ChatColor.RED + "This command can only be executed as a player. You have been identified as " + sender.getName());
        }
        return true;
    }
}
