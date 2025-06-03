package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.BlbiLogin;
import com.blbilink.blbilogin.modules.Sqlite;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;


public class InfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage("/info <player>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage("Player not found");
            return true;
        }
        String uuid = target.getUniqueId().toString();
        int joins = Sqlite.getSqlite().getJoinCount(uuid);
        if (joins == 0) {
            joins = target.getStatistic(Statistic.LEAVE_GAME) + 1;
        }
        long serverUptime = System.currentTimeMillis() - BlbiLogin.serverStartTime;
        long osUptime = System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().getStartTime();
        Long first = Sqlite.getSqlite().getFirstJoin(uuid);
        if (first == null) first = target.getFirstPlayed();
        String firstJoined = first > 0 ? DateTimeFormatter.ISO_LOCAL_DATE.format(Instant.ofEpochMilli(first)) : "Unknown";
        String msg = String.format("%s has joined %d times. First join: %s", target.getName(), joins, firstJoined);
        String serverUp = formatDuration(serverUptime);
        String osUp = formatDuration(osUptime);
        sender.sendMessage(msg);
        sender.sendMessage("Server uptime: " + serverUp);
        sender.sendMessage("System uptime: " + osUp);
        return true;
    }

    private String formatDuration(long millis) {
        Duration d = Duration.ofMillis(millis);
        long days = d.toDays();
        long hours = d.minusDays(days).toHours();
        long minutes = d.minusDays(days).minusHours(hours).toMinutes();
        return days + "d " + hours + "h " + minutes + "m";
    }
}
