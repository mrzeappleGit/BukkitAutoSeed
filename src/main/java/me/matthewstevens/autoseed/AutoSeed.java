package me.matthewstevens.autoseed;

import me.matthewstevens.autoseed.listeners.plantingListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import me.matthewstevens.autoseed.commands.*;

import java.util.List;
import java.util.logging.Logger;

public final class AutoSeed extends JavaPlugin {

    public static Logger log = Bukkit.getLogger();

    public static int radius;
    public static List<String> enabledPlayers;
    public static AutoSeed plugin;




    @Override
    public void onEnable() {
        //config file setup
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        plugin = this;
        //initialize variables
        reloadConfig.loadVars();

        getServer().getPluginManager().registerEvents(new plantingListener(), this);

        //Register Commands
        getCommand("autoseedreload").setExecutor(new reloadConfig());
        getCommand("autoseedtoggle").setExecutor(new playerToggle());
        getCommand("autoseedradius").setExecutor(new setRadius());
    }

}