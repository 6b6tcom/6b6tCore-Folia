package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.BlbiLogin;
import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * Simple vanish command for administrators.
 */
public class VanishCommand implements CommandExecutor {
    private final BlbiLogin plugin = BlbiLogin.plugin;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }
        if (!player.hasPermission("blbilogin.vanish") && !player.isOp()) {
            player.sendMessage("You do not have permission to use this command");
            return true;
        }

        boolean vanish = Configvar.vanishPlayers.contains(player.getName());
        if (vanish) {
            Configvar.vanishPlayers.remove(player.getName());
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(plugin, player);
            }
            player.sendMessage("You are now visible");
        } else {
            Configvar.vanishPlayers.add(player.getName());
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(player)) {
                    online.hidePlayer(plugin, player);
                }
            }
            player.sendMessage("You have vanished");
        }
        return true;
    }
}
