package me.matthewstevens.autoseed.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.matthewstevens.autoseed.AutoSeed;
import org.bukkit.ChatColor;

import static me.matthewstevens.autoseed.AutoSeed.enabledPlayers;
import static me.matthewstevens.autoseed.AutoSeed.plugin;

public class playerToggle implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            String playerName = ChatColor.stripColor(p.getDisplayName());
            if(enabledPlayers.contains(playerName)){
                enabledPlayers.remove(playerName);
                p.sendMessage("[AutoSeed] You have turned Auto Seed off for yourself");
            }else{
                enabledPlayers.add(playerName);
                p.sendMessage("[AutoSeed] You have turned Auto Seed on for yourself");
            }
            plugin.getConfig().set("enabled-players", enabledPlayers);
            plugin.saveConfig();
            reloadConfig.loadVars();
        }
        return true;
    }
}