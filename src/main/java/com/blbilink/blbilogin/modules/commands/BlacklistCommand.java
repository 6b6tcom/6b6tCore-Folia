package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.Sqlite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class BlacklistCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || !player.isOp()) {
            sender.sendMessage("Only operators can use this command");
            return true;
        }
        if (args.length != 1) {
            player.sendMessage("/blacklist <username>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        Sqlite.getSqlite().setBlacklisted(target.getUniqueId().toString(), true);
        if (target.isOnline()) {
            ((Player) target).kickPlayer("Disconnected: Internal server error");
        }
        player.sendMessage(target.getName() + " has been blacklisted");
        return true;
    }
}
