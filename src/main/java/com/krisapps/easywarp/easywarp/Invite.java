package com.krisapps.easywarp.easywarp;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Invite implements CommandExecutor {

    EasyWarp main;
    public Invite(EasyWarp main){
        this.main = main;
    }

    //Create new invite with given arguments.
    static void createInvite(EasyWarp main, CommandSender sender, String inviteID, String playerName, int uses, String warpID) throws IOException {
        main.warps.set("invites." + inviteID + ".recipient", playerName);
        main.warps.set("invites." + inviteID + ".uses", uses);
        main.warps.set("invites." + inviteID + ".warp", warpID);
        main.warps.save(main.warpfile);
        File warpFile = new File("plugins/EasyWarp/" + sender.getName() + ".yml");
        FileConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);
        TextComponent warpClickable = new TextComponent(TextComponent.fromLegacyText("[Warp to " + warpID + "]", net.md_5.bungee.api.ChatColor.AQUA));
        warpClickable.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        warpClickable.setBold(true);
        warpClickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/invitewarp " + playerName + " " + warpID));
        sender.sendMessage(ChatColor.GREEN + "Successfully sent an invite to player: " + ChatColor.AQUA + playerName);
        Bukkit.getServer().getPlayer(playerName).sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have received an invite from&b " + sender.getName() + "&a:\n&bWarp: &e" + warpID + "\n&bUses: &e" + uses));
        Bukkit.getServer().getPlayer(playerName).spigot().sendMessage(warpClickable);

    }

    //Return list containing all invites.
    static ArrayList<String> retrieveInviteList(EasyWarp main){
        return new ArrayList<>(main.warps.getConfigurationSection("invites").getKeys(false));
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Syntax: /invite <create/list> [warpID] <playerName> <useCount>
        File warpFile = new File("plugins/EasyWarp/" + sender.getName() + ".yml");
        FileConfiguration warps = YamlConfiguration.loadConfiguration(warpFile);
        if (args.length > 0){
                switch (args[0]){
                    case "create":
                        if (warps.contains("warps." + args[1])){
                            if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getPlayer(args[2]))){
                                if (args[3] != null){
                                    try {
                                        createInvite(main, sender, args[1] + "-" + args[2], args[2], Integer.parseInt(args[3]), args[1]);
                                    } catch (IOException e) {
                                        sender.sendMessage(ChatColor.RED + "Failed to create invite. Check the console for more info.");
                                    }
                                }
                            }else{
                                sender.sendMessage(ChatColor.RED + "Cannot create invite for nonexistent player.");
                            }
                        }else{
                            sender.sendMessage(ChatColor.RED + "Cannot create invite for nonexistent warp.");
                        }
                        break;
                    case "list":
                        sender.sendMessage(ChatColor.YELLOW + "=============All invites=============");
                        sender.sendMessage(ChatColor.GREEN + "Format: warpID-invited_playerName");
                        for (String invite: retrieveInviteList(main)){
                            sender.sendMessage(ChatColor.AQUA + invite + "\n");
                        }
                        sender.sendMessage(ChatColor.YELLOW + "=====================================");
                        break;
                    default:
                    sender.sendMessage(ChatColor.RED + "Unknown operation " + ChatColor.YELLOW + args[0]);
                }
        }else{
            sender.sendMessage(ChatColor.RED + "Syntax error: Incorrect amount of arguments provided.\nNeeded at least: 1\nProvided: " + args.length);
        }


        return true;
    }
}
