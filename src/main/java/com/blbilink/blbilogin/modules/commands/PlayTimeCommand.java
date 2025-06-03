package com.blbilink.blbilogin.modules.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PlayTimeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String targetName = args.length > 0 ? args[0] : sender.getName();
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage("Player not found");
            return true;
        }
        long ticks = target.getStatistic(Statistic.PLAY_ONE_MINUTE);
        long hours = ticks / 20 / 3600;
        long minutes = (ticks / 20 / 60) % 60;
        sender.sendMessage(target.getName() + " playtime: " + hours + "h " + minutes + "m");
        return true;
    }
}
