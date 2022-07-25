package com.krisapps.easywarp.easywarp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class InviteWarp implements CommandExecutor {

    EasyWarp main;
    public InviteWarp(EasyWarp main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /invitewarp <playerName> <warpID>
        if (args.length == 2) {
            if (sender.hasPermission("easywarp.usesyscommands")) {
                String player = args[0];
                Player senderAsPlayer = (Player) sender;
                File warpFile = new File("plugins/EasyWarp/" + player + ".yml");
                FileConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);
                if (main.warps.getInt("invites." + args[1] + "-" + sender.getName() + ".uses") > 0) {
                switch (Objects.requireNonNull(warps.getString("warps." + args[1] + ".location.dimension"))) {
                    case "NORMAL":
                        senderAsPlayer.teleport(new Location(Bukkit.getWorld(Objects.requireNonNull(main.warps.getString("globals.main-world"))), Double.parseDouble(warps.getString("warps." + args[1] + ".location.x")), Double.parseDouble(warps.getString("warps." + args[1] + ".location.y")), Double.parseDouble(warps.getString("warps." + args[1] + ".location.z"))));
                        break;
                    case "NETHER":
                    case "THE_END":
                        senderAsPlayer.teleport(new Location(Bukkit.getWorld(main.warps.getString("globals.main-world") + "_" + String.valueOf(warps.getString("warps." + args[1] + ".location.dimension")).toLowerCase(Locale.ROOT)), Double.parseDouble(warps.getString("warps." + args[1] + ".location.x")), Double.parseDouble(warps.getString("warps." + args[1] + ".location.y")), Double.parseDouble(warps.getString("warps." + args[1] + ".location.z"))));
                        break;
                }
                    main.warps.set("invites." + args[1] + "-" + sender.getName() + ".uses", main.warps.getInt("invites." + args[1] + "-" + sender.getName() + ".uses") - 1);
                    try {
                        main.warps.save(main.warpfile);
                    } catch (IOException e) {
                        sender.sendMessage(ChatColor.RED + "An error has occurred while saving data. Check the console for details.");
                        e.printStackTrace();
                    }
                    sender.sendMessage(ChatColor.GREEN + "You have warped to " + ChatColor.AQUA + args[1] + ChatColor.GREEN + ". Current invite has " + ChatColor.YELLOW + main.warps.getString("invites." + args[1] + "-" + sender.getName() + ".uses") + ChatColor.GREEN + " more uses.");
                }else{
                    sender.sendMessage(ChatColor.RED + "This invite is no longer valid. Request a new invite from the owner.");
                }}
        }else{
            sender.sendMessage("Syntax error: Incorrect amount of arguments provided.\nNeeded: 2\nProvided: " + args.length);
        }
        return true;
    }
}
