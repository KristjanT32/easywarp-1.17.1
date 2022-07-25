package com.krisapps.easywarp.easywarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Warp implements CommandExecutor {

    File warpfile;
    File configfile;
    EasyWarp main;

    public Warp(EasyWarp main, File warpfile, File configfile) {
        this.main = main;
        this.warpfile = warpfile;
        this.configfile = configfile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /warp <id>
        String warpID;
        String x;
        String y;
        String z;
        String display_name;
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
        if (sender instanceof Player) {
            if (args.length == 1) {
                YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
                warpID = args[0];
                if (warpID != null && warps.contains("warps." + warpID)) {
                    //Path: warps.warpID.param.subparam
                    Player player = (Player) sender;
                    x = warps.getString("warps." + warpID + ".location.x");
                    y = warps.getString("warps." + warpID + ".location.y");
                    z = warps.getString("warps." + warpID + ".location.z");
                    display_name = warps.getString("warps." + warpID + ".display_name");
                    if (config.getBoolean("rules.show-warping-message")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.warping-message")).replace("$target", "&r" + warpID)));
                    }
                    warps.set("latestLocation." + sender.getName() + ".x", player.getLocation().getX());
                    warps.set("latestLocation." + sender.getName() + ".y", player.getLocation().getY());
                    warps.set("latestLocation." + sender.getName() + ".z", player.getLocation().getZ());
                    warps.set("latestLocation." + sender.getName() + ".dimension", Objects.requireNonNull(player.getLocation().getWorld()).getEnvironment().toString());
                    try {
                        warps.save(warpfile);
                    } catch (IOException e) {
                        sender.sendMessage(ChatColor.RED + "Failed to save your current coordinates. Back command will not teleport you back.");
                        e.printStackTrace();
                    }
                    try {
                        switch (Objects.requireNonNull(warps.getString("warps." + warpID + ".location.dimension"))) {
                            case "NORMAL":
                                player.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(warps.getString("globals.main-world"))), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
                                break;
                            case "NETHER":
                            case "THE_END":
                                player.teleport(new Location(Bukkit.getWorld(warps.getString("globals.main-world") + "_" + String.valueOf(warps.getString("warps." + warpID + ".location.dimension")).toLowerCase(Locale.ROOT)), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
                                break;
                        }


                        if (config.getBoolean("rules.show-arrival-message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.arrival-message")).replace("$warp", "&r" + display_name)));
                        }
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage(ChatColor.RED + "Something went wrong warping to " + ChatColor.YELLOW + warpID);
                        e.printStackTrace();
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Cannot find warp " + ChatColor.YELLOW + warpID + ChatColor.RED + "");
                }
            } else if (args.length == 2) {
                if (args[1].equals("-p")) {
                    File pwwarpfile = new File(main.getDataFolder(), sender.getName() + ".yml");
                    YamlConfiguration warps = YamlConfiguration.loadConfiguration(pwwarpfile);
                    warpID = args[0];
                    if (warpID != null && warps.contains("warps." + warpID)) {
                        //Path: warps.warpID.param.subparam
                        Player player = (Player) sender;
                        x = warps.getString("warps." + warpID + ".location.x");
                        y = warps.getString("warps." + warpID + ".location.y");
                        z = warps.getString("warps." + warpID + ".location.z");
                        display_name = warps.getString("warps." + warpID + ".display_name");
                        if (config.getBoolean("rules.show-warping-message")) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.warping-message")).replace("$target", "&r" + warpID)));
                        }
                        warps.set("latestLocation." + sender.getName() + ".x", player.getLocation().getX());
                        warps.set("latestLocation." + sender.getName() + ".y", player.getLocation().getY());
                        warps.set("latestLocation." + sender.getName() + ".z", player.getLocation().getZ());
                        warps.set("latestLocation." + sender.getName() + ".dimension", Objects.requireNonNull(player.getLocation().getWorld()).getEnvironment().toString());
                        try {
                            warps.save(pwwarpfile);
                            main.getLogger().info("Saved " + pwwarpfile.getName());
                        } catch (IOException e) {
                            sender.sendMessage(ChatColor.RED + "Failed to save your current coordinates. Back command will not teleport you back.");
                            e.printStackTrace();
                        }
                        try {
                            switch (Objects.requireNonNull(warps.getString("warps." + warpID + ".location.dimension"))) {
                                case "NORMAL":
                                    player.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(warps.getString("globals.main-world"))), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
                                    break;
                                case "NETHER":
                                case "THE_END":
                                    player.teleport(new Location(Bukkit.getWorld(warps.getString("globals.main-world") + "_" + String.valueOf(warps.getString("warps." + warpID + ".location.dimension")).toLowerCase(Locale.ROOT)), Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
                                    break;
                            }


                            if (config.getBoolean("rules.show-arrival-message")) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.arrival-message")).replace("$warp", "&r" + display_name)));
                            }
                        } catch (IllegalArgumentException e) {
                            sender.sendMessage(ChatColor.RED + "Something went wrong warping to " + ChatColor.YELLOW + warpID);
                            e.printStackTrace();
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Cannot find warp " + ChatColor.YELLOW + warpID + ChatColor.RED + "");
                    }
                } else {
                    sender.sendMessage(ChatColor.RED + "Unknown parameter " + ChatColor.YELLOW + args[0]);
                }
            }
        }else{
            sender.sendMessage(ChatColor.RED + "This command can only be executed as a player. You have been identified as " + sender.getName());
        }
        return true;
    }
}
