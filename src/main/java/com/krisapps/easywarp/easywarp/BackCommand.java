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
import java.util.Locale;
import java.util.Objects;

public class BackCommand implements CommandExecutor {

    final File warpfile;
    final File configfile;
    final EasyWarp main;

    public BackCommand(File warpfile, File configfile, EasyWarp main) {
        this.warpfile = warpfile;
        this.configfile = configfile;
        this.main = main;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /back
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configfile);
        if (warps.contains("latestLocation." + sender.getName())) {
            Player player = (Player) sender;
            double x = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".x")));
            double y = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".y")));
            double z = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".z")));
            if (warps.contains("latestLocation." + sender.getName() + ".dimension")) {
                switch (Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".dimension"))) {
                    case "NORMAL":
                        player.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(warps.getString("globals.main-world"))), x, y, z));
                        break;
                    case "NETHER":
                    case "THE_END":
                        player.teleport(new Location(Bukkit.getWorld(warps.getString("globals.main-world") + "_" + String.valueOf(warps.getString("latestLocation." + sender.getName() + ".dimension")).toLowerCase(Locale.ROOT)), x, y, z));
                        break;
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Sorry, but there is no data about your previous location available.");
            }
            if (config.getBoolean("rules.show-back-message")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.back-message"))));
            }
        } else {
                if (!warps.contains("latestLocation." + sender.getName())) {
                    File pw = new File(main.getDataFolder(), sender.getName() + ".yml");
                    warps = YamlConfiguration.loadConfiguration(pw);
                    Player player = (Player) sender;
                    double x = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".x")));
                    double y = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".y")));
                    double z = Double.parseDouble(Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".z")));
                    if (warps.contains("latestLocation." + sender.getName() + ".dimension")) {
                        switch (Objects.requireNonNull(warps.getString("latestLocation." + sender.getName() + ".dimension"))) {
                            case "NORMAL":
                                player.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(warps.getString("globals.main-world"))), x, y, z));
                                break;
                            case "NETHER":
                            case "THE_END":
                                player.teleport(new Location(Bukkit.getWorld(warps.getString("globals.main-world") + "_" + String.valueOf(warps.getString("latestLocation." + sender.getName() + ".dimension")).toLowerCase(Locale.ROOT)), x, y, z));
                                break;
                        }
                    } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(config.getString("warp-messages.back-message-cannot"))));
                }
            }
        }
        return true;
    }
}
