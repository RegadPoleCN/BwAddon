package me.regadpole.bwaddon;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BoundingBox;
import org.screamingsandals.bedwars.api.BedwarsAPI;
import org.screamingsandals.bedwars.api.game.ItemSpawner;

import java.util.ArrayList;
import java.util.List;

public final class BwAddon extends JavaPlugin {

    public static BwAddon INSTANCE;
    public static BedwarsAPI api;

    @Override
    public void onLoad() {
        INSTANCE = this;
        saveDefaultConfig();

    }

    @Override
    public void onEnable() {
        Configurator.loadConfig();

        if (Bukkit.getPluginManager().getPlugin("BedWars") == null){
            getLogger().warning("Can't find BedWars plugin. Disable now.");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        api = BedwarsAPI.getInstance();
        getLogger().info("Hook into BedWars plugin.");

        Bukkit.getPluginManager().registerEvents(new ServerListeners(), this);
        getLogger().info("Registered events.");
        Bukkit.getServer().getPluginCommand("bwaddon").setExecutor(new BwCommands());
        getLogger().info("Registered commands.");

        getLogger().info("BwAddon enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BwAddon disabled.");
    }

}
