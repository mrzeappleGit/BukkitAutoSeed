package me.matthewstevens.autoseed.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.matthewstevens.autoseed.AutoSeed.plugin;
import static me.matthewstevens.autoseed.AutoSeed.radius;

public class setRadius implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (p.hasPermission("autoseed.admin")) {
                if (args.length == 0) {
                    p.sendMessage("You did not provide anyone to add to the list.");
                    p.sendMessage("Example: /autoseedradius <max radius>");
                } else if (args.length > 1) {
                    p.sendMessage("You added too many arguments to the command.");
                    p.sendMessage("Example: /autoseedradius <max radius>");
                } else{
                    try{
                        Integer.parseInt(args[0]);
                        radius = Integer.parseInt(args[0]);
                        p.sendMessage("[AutoSeed] Will now affect " + args[0] + " block radius");
                        plugin.getConfig().set("radius", radius);
                        plugin.saveConfig();
                        reloadConfig.loadVars();
                    }
                    catch (NumberFormatException e){
                        p.sendMessage("Radius must be a whole number");
                    }
                }
            }else {
                p.sendMessage("You do not have permission for this command.");
            }
        }
        return true;
    }
}
