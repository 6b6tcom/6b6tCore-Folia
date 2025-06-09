package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * Displays server statistics.
 */
public class JoinStatsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!command.getName().equalsIgnoreCase("joinstats")) {
            return false;
        }
        String prefix = Configvar.config.getString("prefix");
        sender.sendMessage(prefix + "Total joins: " + Configvar.joinCount);
        return true;
    }
}
