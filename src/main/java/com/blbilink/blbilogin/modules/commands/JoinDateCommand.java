package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.Sqlite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class JoinDateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String targetName = args.length > 0 ? args[0] : sender.getName();
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage("Player not found");
            return true;
        }
        Long ts = Sqlite.getSqlite().getFirstJoin(target.getUniqueId().toString());
        if (ts == null) ts = target.getFirstPlayed();
        String date = ts > 0 ? DateTimeFormatter.ISO_LOCAL_DATE.format(Instant.ofEpochMilli(ts)) : "Unknown";
        sender.sendMessage(target.getName() + " first joined on " + date);
        return true;
    }
}
