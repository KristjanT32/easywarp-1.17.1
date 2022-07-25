package com.krisapps.easywarp.easywarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class EditWarp implements CommandExecutor {

    File warpfile;
    EasyWarp main;

    public EditWarp(EasyWarp main, File warpfile) {
        this.main = main;
        this.warpfile = warpfile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /editwarp <id> <param> <value>
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        if (sender instanceof Player) {
            String param;
            String value;
            String warpID;
            Player player = (Player) sender;
            if (args.length > 0) {
                    warpID = args[0];
                    param = args[1];
                    if (warps.contains("warps." + warpID)) {
                        if ("position".equals(param)) {
                            warps.set("warps." + warpID + ".location.x", player.getLocation().getX());
                            warps.set("warps." + warpID + ".location.y", player.getLocation().getY());
                            warps.set("warps." + warpID + ".location.z", player.getLocation().getZ());
                            try {
                                warps.save(warpfile);
                            } catch (IOException e) {
                                sender.sendMessage(ChatColor.RED + "Error saving warp information.");
                                e.printStackTrace();
                            }
                            sender.sendMessage(ChatColor.GREEN + "Successfully updated the location of the warp: " + ChatColor.AQUA + "\nX: " + player.getLocation().getX() + "\nY: " + player.getLocation().getY() + "\nZ: " + player.getLocation().getZ());
                        } else {
                            value = "";
                            for (int i = 2; i != args.length; i++) {
                                value += args[i] + " ";
                            }
                            if (warps.contains("warps." + warpID + "." + param)) {
                                warps.set("warps." + warpID + "." + param, value);
                                try {
                                    warps.save(warpfile);
                                } catch (IOException e) {
                                    sender.sendMessage(ChatColor.RED + "Error saving warp information.");
                                    e.printStackTrace();
                                }
                                sender.sendMessage("Value of " + ChatColor.YELLOW + param + ChatColor.GREEN + " successfully set to " + ChatColor.AQUA + value);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Parameter " + ChatColor.YELLOW + param + ChatColor.RED + " cannot be found.");
                            }
                        }
                    }else{
                        sender.sendMessage(ChatColor.RED + "This warp does not exist.");
                    }
            }else{
                sender.sendMessage(ChatColor.RED + "Syntax error: Incorrect amount of arguments provided.\nNeeded at least: 2\nProvided: " + args.length);
            }
        }else{
            sender.sendMessage(ChatColor.RED + "This command can only be executed as a player. You have been identified as " + sender.getName());
        }
        return true;
    }
}
