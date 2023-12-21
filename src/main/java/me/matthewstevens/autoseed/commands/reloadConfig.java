package me.matthewstevens.autoseed.commands;

import me.matthewstevens.autoseed.AutoSeed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.matthewstevens.autoseed.AutoSeed.plugin;

public class reloadConfig implements CommandExecutor {
    public static void loadVars(){
        AutoSeed.radius = plugin.getConfig().getInt("radius");
        AutoSeed.enabledPlayers = plugin.getConfig().getStringList("enabled-players");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("AutoSeed.admin")) {
                loadVars();
                p.sendMessage("[AutoSeed] Config reloaded.");
            }else {
                p.sendMessage("You do not have permission for this command.");
            }
        }
        return true;
    }
}
