package io.github.spaery.simplerhomes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Homes implements CommandExecutor {

    private final SimplerHomes instance = SimplerHomes.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player player) {
                HomeFile h = new HomeFile();
                h.homes(player);
            } else {
                sender.sendMessage("Command must be executed by a player.");
            }
            return true;
        } else if ((args.length == 1 || args.length == 2) && sender.hasPermission("simplerhomes.homes.other")) {
            OfflinePlayer player = instance.getOfflinePlayer(args[0]);
            if (player != null) {
                HomeFile h = new HomeFile();
                if (args.length == 1) {
                    h.homes(player, sender);
                } else if(sender instanceof Player spl){
                    try {
                        String configPath = player.getName() + "." + args[1];
                        Location loc = new Location(
                                Bukkit.getWorld(h.homes.getString(configPath + ".World")),
                                h.homes.getDouble(configPath + ".X"),
                                h.homes.getDouble(configPath + ".Y"),
                                h.homes.getDouble(configPath + ".Z"),
                                (float) h.homes.getDouble(configPath + ".Yaw"),
                                (float) h.homes.getDouble(configPath + ".Pitch"));
                        spl.teleport(loc);
                    } catch (IllegalArgumentException e) {
                        sender.sendMessage("Home '" + args[1] + "' is invalid or cannot be found");
                    }
                }else{
                    sender.sendMessage("Command must be executed by a player.");
                }
            } else {
                sender.sendMessage("Le joueur n'existe pas");
            }
            return true;
        }
        return false;
    }
}
