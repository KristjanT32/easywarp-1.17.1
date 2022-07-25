package com.krisapps.easywarp.easywarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CreatePersonalWarp implements CommandExecutor {
    //Syntax: /createpw <id> <display_name>
    //Creates a personal warp, not visible to others.

    EasyWarp main;

    public CreatePersonalWarp(EasyWarp main) {
        this.main = main;
    }

    File warpFile;
    FileConfiguration warps;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            if (args.length >= 2) {
                warpFile = new File(main.getDataFolder(), sender.getName() + ".yml");
                warps = YamlConfiguration.loadConfiguration(warpFile);
                try {
                    warps.load(warpFile);
                } catch (IOException e) {
                    sender.sendMessage(ChatColor.RED + "An error has occurred while reading the file. Check the console for details.");
                    e.printStackTrace();
                } catch (InvalidConfigurationException e) {
                    sender.sendMessage(ChatColor.RED + "Failed to load the config file to save changes. Check the console for details.");
                    e.printStackTrace();
                }
                if (!warpFile.getParentFile().exists() || !warpFile.exists()) {
                    try {
                        warpFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    warps = new YamlConfiguration();
                    try {
                        warps.load(warpFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InvalidConfigurationException e) {
                        e.printStackTrace();
                    }

                    if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NORMAL") {
                        warps.set("globals.main-world", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NETHER") {
                        warps.set("globals.nether", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "THE_END") {
                        warps.set("globals.end", ((Player) sender).getLocation().getWorld().getName());
                    }
                    main.getLogger().info("Created new file for " + sender.getName());
                }
                String warp_displayname = "";
                for (int i = 1; i != args.length; i++) {
                    warp_displayname += args[i] + " ";
                }
                String x = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getX());
                String y = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getY());
                String z = String.valueOf(Bukkit.getPlayer(sender.getName()).getLocation().getZ());
                if (warps != null) {
                    warps.set("warps." + args[0] + "." + "display_name", warp_displayname);
                    warps.set("warps." + args[0] + "." + "id", args[0]);
                    warps.set("warps." + args[0] + "." + "location.x", x);
                    warps.set("warps." + args[0] + "." + "location.y", y);
                    warps.set("warps." + args[0] + "." + "location.z", z);
                    warps.set("warps." + args[0] + ".location.dimension", Bukkit.getPlayer(sender.getName()).getWorld().getEnvironment().toString());
                    warps.set("warps." + args[0] + ".author", sender.getName());
                    try {
                        warps.save(warpFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NORMAL") {
                        warps.set("globals.main-world", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "NETHER") {
                        warps.set("globals.nether", ((Player) sender).getLocation().getWorld().getName());
                    } else if (((Player) sender).getLocation().getWorld().getEnvironment().toString() == "THE_END") {
                        warps.set("globals.end", ((Player) sender).getLocation().getWorld().getName());
                    }
                    sender.sendMessage(ChatColor.GREEN + "Successfully created " + ChatColor.AQUA + args[0] + ChatColor.GREEN + "!\nYou can now warp here by /warp " + args[0] + " -p");
                } else {
                    sender.sendMessage(ChatColor.RED + "Could not acquire the needed configuration file. Please make sure that all the required files are present.");
                }

            } else {
                sender.sendMessage(ChatColor.RED + "Syntax error: Incorrect amount of arguments provided.\nNeeded at least: 2\nProvided: " + args.length);
                return false;
            }

        }else{
            sender.sendMessage(ChatColor.RED + "This command can only be executed as a player. You have been identified as " + sender.getName());
        }
        return true;
    }
}
