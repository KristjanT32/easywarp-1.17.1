package com.krisapps.easywarp.easywarp;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Objects;

public class ListWarps implements CommandExecutor {

    File warpfile;
    EasyWarp main;

    public ListWarps(File warpfile, EasyWarp main) {
        this.warpfile = warpfile;
        this.main = main;
    }
    

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        YamlConfiguration warps = YamlConfiguration.loadConfiguration(warpfile);
        String warpstring = "";
        //IF no arguments are provided, provide global warps.
        if (args.length == 0) {
            //Provide global warps.
            try {
                for (String warp : warps.getConfigurationSection("warps").getKeys(false)) {
                    if (warps.contains("warps." + warp + ".location.dimension")) {
                        if (Objects.equals(warps.getString("warps." + warp + ".location.dimension"), "NORMAL")) { //Is the dimension overworld?
                            warpstring += ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Overworld" + ChatColor.DARK_GREEN + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (warps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + warps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                            } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + "\n" + ChatColor.RESET + "------------------------------------\n";
                            }
                        } else if (Objects.equals(warps.getString("warps." + warp + ".location.dimension"), "NETHER")) { //Is the dimension nether?
                            warpstring += ChatColor.RED + "[" + ChatColor.DARK_RED + "Nether" + ChatColor.RED + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (warps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + warps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                            } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + "\n" + ChatColor.RESET + "------------------------------------\n";
                            }
                        } else if (Objects.equals(warps.getString("warps." + warp + ".location.dimension"), "THE_END")) { //Is the dimension the end?
                            warpstring += ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "End" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (warps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + warps.getString("warps." + warp + ".author") + "\n---\n";
                            } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + "\n" + ChatColor.RESET + "------------------------------------\n";
                            }
                        }
                    }else {
                        warpstring += ChatColor.RED + "[" + ChatColor.DARK_RED + "Unknown Dimension" + ChatColor.RED + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                        if (warps.contains("warps." + warp + ".author")) {
                            warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + warps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                        } else {
                            warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + "\n" + ChatColor.RESET + "------------------------------------\n";
                        }
                        main.getLogger().warning(ChatColor.RED + String.format("It appears, that there is no dimensional info provided for %s. Maybe it is from an older version?", warp));
                    }
                }
                sender.sendMessage(ChatColor.YELLOW + "=======================================\n" + ChatColor.AQUA + "List of available warps\n" + ChatColor.GREEN + "\n" + warpstring + ChatColor.AQUA + "\nA total of " + ChatColor.BOLD + warps.getConfigurationSection("warps").getKeys(false).toArray().length + ChatColor.AQUA + " warps." + ChatColor.YELLOW + "\n=======================================");
                return true;
            } catch (NullPointerException e) {
                sender.sendMessage(ChatColor.RED + "There are no global warps warps saved.");
            }
        }else if (args.length > 0){
            File pWFile = new File(main.getDataFolder(), sender.getName() + ".yml");
            YamlConfiguration pWarps = YamlConfiguration.loadConfiguration(pWFile);
            //Check whether to provide personal or global warps.
            if (args[0].equalsIgnoreCase("-p")){
                // Provide personal warps.
                try {
                    for (String warp : pWarps.getConfigurationSection("warps").getKeys(false)) {
                        if (Objects.equals(pWarps.getString("warps." + warp + ".location.dimension"), "NORMAL")) {
                            //Construct warp string.
                            warpstring += ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Overworld" + ChatColor.DARK_GREEN + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (pWarps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + pWarps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                                } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + ChatColor.RESET + "\n------------------------------------\n";
                                }
                        } else if (Objects.equals(pWarps.getString("warps." + warp + ".location.dimension"), "NETHER")) {
                            warpstring += ChatColor.RED + "[" + ChatColor.DARK_RED + "Nether" + ChatColor.RED + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (pWarps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + pWarps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                                } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + ChatColor.RESET + "\n------------------------------------\n";
                                }
                        } else if (Objects.equals(pWarps.getString("warps." + warp + ".location.dimension"), "THE_END")) {
                            warpstring += ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "End" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.LIGHT_PURPLE + warp + "\n";
                            if (pWarps.contains("warps." + warp + ".author")) {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + pWarps.getString("warps." + warp + ".author") + ChatColor.RESET + "\n------------------------------------\n";
                                } else {
                                warpstring += "\n" + ChatColor.AQUA + "> Author: " + ChatColor.LIGHT_PURPLE + "N/A" + ChatColor.RESET + "\n------------------------------------\n";
                                }
                        }
                    }
                    sender.sendMessage(ChatColor.YELLOW + "=======================================\n" + ChatColor.AQUA + "List of available personal warps\n" + ChatColor.GREEN + "\n" + warpstring + ChatColor.AQUA + "\nA total of " + ChatColor.BOLD + pWarps.getConfigurationSection("warps").getKeys(false).toArray().length + ChatColor.AQUA + " personal warps." + ChatColor.YELLOW + "\n=======================================");
                    return true;
                }catch (NullPointerException e){
                    sender.sendMessage(ChatColor.RED + "There are no global warps warps saved.");
                }
            }else{
                sender.sendMessage(ChatColor.RED + "Unknown parameter " + ChatColor.YELLOW + args[0]);
                return false;
            }
        }
        return true;
    }
}
