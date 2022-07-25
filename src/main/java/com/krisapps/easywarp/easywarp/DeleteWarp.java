package com.krisapps.easywarp.easywarp;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class DeleteWarp implements CommandExecutor {

    File warpfile;
    EasyWarp main;

    public DeleteWarp(File warpfile, EasyWarp main) {
        this.warpfile = warpfile;
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /deletewarp <id>
        if (args.length > 0) {
            if (args.length == 1) {
                YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
                if (args[0] != null) {
                    if (warps.contains("warps." + args[0])) {
                        //Delete warp
                        warps.set("warps." + args[0], null);
                        try {
                            warps.save(warpfile);
                        } catch (IOException e) {
                            sender.sendMessage(ChatColor.RED + "An error occurred while saving changes. Check the console for more info.");
                            e.printStackTrace();
                        }
                        sender.sendMessage(ChatColor.RED + "Successfully deleted " + ChatColor.YELLOW + args[0] + ChatColor.RED + ".");
                    } else {
                        sender.sendMessage(ChatColor.RED + "No such warp exists. (" + ChatColor.YELLOW + args[0] + ChatColor.RED + ")");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "No warp id provided.");
                }
            } else if (args.length == 2) {
                if (args[0].equalsIgnoreCase("-p")) {
                    File pw = new File(main.getDataFolder(), sender.getName() + ".yml");
                    YamlConfiguration warps = YamlConfiguration.loadConfiguration(pw);
                    if (args[1] != null) {
                        if (warps.contains("warps." + args[1])) {
                            //Delete warp
                            warps.set("warps." + args[1], null);
                            try {
                                warps.save(pw);
                            } catch (IOException e) {
                                sender.sendMessage(ChatColor.RED + "An error occurred while saving changes. Check the console for more info.");
                                e.printStackTrace();
                            }
                            sender.sendMessage(ChatColor.RED + "Successfully deleted " + ChatColor.YELLOW + args[1] + ChatColor.RED + ".");
                        } else {
                            sender.sendMessage(ChatColor.RED + "No such warp exists. (" + ChatColor.YELLOW + args[1] + ChatColor.RED + ")");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "No warp id was provided.");
                    }
                } else {
                    sender.sendMessage(ChatColor.YELLOW + args[0] + ChatColor.RED + " is not a valid parameter.");
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "Syntax error: Incorrect amount of arguments provided.\nNeeded at least: 1\nProvided: " + args.length);
            return false;
        }
        return true;
    }
}
