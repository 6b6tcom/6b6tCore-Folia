package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.vars.Configvar;
import com.blbilink.blbilogin.BlbiLogin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class WorldStatsCommand implements CommandExecutor {
    private static final LocalDate START_DATE = LocalDate.of(2025, 5, 5);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("worldstats") && !command.getName().equalsIgnoreCase("info") && !command.getName().equalsIgnoreCase("stats")) {
            return false;
        }

        long days = ChronoUnit.DAYS.between(START_DATE, LocalDate.now());
        String age = days + " days";

        World world = Bukkit.getWorlds().get(0);
        File folder = world.getWorldFolder();
        long sizeBytes = folderSize(folder);
        String size = humanReadableByteCount(sizeBytes);

        long uptimeMillis = System.currentTimeMillis() - BlbiLogin.serverStartTime;
        String uptime = formatDuration(uptimeMillis);

        String prefix = Configvar.config.getString("prefix");
        sender.sendMessage(prefix + "World size: " + size + ", Age: " + age);
        sender.sendMessage(prefix + "Uptime: " + uptime + ", Joins: " + BlbiLogin.totalJoins);
        return true;
    }

    private long folderSize(File directory) {
        long length = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += folderSize(file);
                }
            }
        }
        return length;
    }

    private String humanReadableByteCount(long bytes) {
        double b = bytes;
        String[] units = {"B", "KB", "MB", "GB", "TB"};
        int i = 0;
        while (b >= 1024 && i < units.length - 1) {
            b /= 1024;
            i++;
        }
        return String.format("%.2f %s", b, units[i]);
    }

    private String formatDuration(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        hours %= 24;
        minutes %= 60;
        seconds %= 60;
        return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
    }
}
