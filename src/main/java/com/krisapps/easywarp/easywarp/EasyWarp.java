package com.krisapps.easywarp.easywarp;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class EasyWarp extends JavaPlugin {

    File file = new File(this.getDataFolder(), "config.yml");
    File warpfile = new File(this.getDataFolder(), "warps.yml");

    FileConfiguration warps;
    FileConfiguration plugin;

    void createFiles() throws IOException {
        if (!file.exists() && !file.getParentFile().exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            saveResource("config.yml", false);
            this.getLogger().info("Created new plugin config at " + file.getPath());
        } else if (!file.exists() && file.getParentFile().exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            saveResource("config.yml", false);
            this.getLogger().info("Created new plugin config at " + file.getPath());
        } else {
            this.getLogger().info("Found existing plugin config.");
        }
        if (!warpfile.exists() && !warpfile.getParentFile().exists()) {
            if (!warpfile.getParentFile().exists()) {
                warpfile.getParentFile().mkdirs();
            }
            saveResource("warps.yml", false);
            this.getLogger().info("Created new warp config at " + warpfile.getPath());
        } else if (!warpfile.exists() && warpfile.getParentFile().exists()) {
            if (!warpfile.getParentFile().exists()) {
                warpfile.getParentFile().mkdirs();
            }
            saveResource("warps.yml", false);
            this.getLogger().info("Created new warp file at " + file.getPath());
        } else {
            this.getLogger().info("Found existing warp config.");
        }

        warps = new YamlConfiguration();
        plugin = new YamlConfiguration();
        try {
            warps.load(warpfile);
            plugin.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

    }

    void register() {
        //Register plugin commands.
        getCommand("createwarp").setExecutor(new CreateWarp(this, warpfile));
        getCommand("warp").setExecutor(new Warp(this, warpfile, file));
        getCommand("editwarp").setExecutor(new EditWarp(this, warpfile));
        getCommand("deletewarp").setExecutor(new DeleteWarp(warpfile, this));
        getCommand("listwarps").setExecutor(new ListWarps(warpfile, this));
        getCommand("back").setExecutor(new BackCommand(warpfile, file, this));
        getCommand("createpw").setExecutor(new CreatePersonalWarp(this));
        getCommand("editpw").setExecutor(new EditPersonalWarp(this));
        getCommand("invite").setExecutor(new Invite(this));
        getCommand("invitewarp").setExecutor(new InviteWarp(this));
        getCommand("setdimension").setExecutor(new SetDimension(this));
        getCommand("setpersonaldimension").setExecutor(new SetPersonalDimension(this));
        getLogger().info(ChatColor.GREEN + "Successfully registered commands for " + getDescription().getName() + " v" + getDescription().getVersion());
        setTabAutoComplete();
    }

    void setTabAutoComplete(){
        getCommand("setdimension").setTabCompleter(new SetDimAC(this));
        getCommand("setpersonaldimension").setTabCompleter(new SetPersonalDimAC(this));
        getCommand("deletewarp").setTabCompleter(new DelWarpAC(this));
        getCommand("listwarps").setTabCompleter(new ListAC());
        getCommand("invite").setTabCompleter(new InviteAC());
        getCommand("editpw").setTabCompleter(new EditPWAC(this));
        getCommand("editwarp").setTabCompleter(new EditAC(this));
        getLogger().info(ChatColor.GREEN + "Successfully registered tab autocompletion for " + getDescription().getName() + " v" + getDescription().getVersion());
    }

    @Override
    public void onEnable() {
        this.getLogger().info(ChatColor.GREEN + "Enabled " + getDescription().getName() + " v" + getDescription().getVersion());
        register();
        try {
            createFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        this.getLogger().info(ChatColor.GREEN + "Disabled " + getDescription().getName() + " v" + getDescription().getVersion());
    }
}
