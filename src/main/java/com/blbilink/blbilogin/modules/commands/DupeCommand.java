package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.events.PistonDupe;
import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DupeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(Configvar.config.getString("prefix") + "You do not have permission.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(Configvar.config.getString("prefix") + "/dupe <on|off>");
            return true;
        }
        if (args[0].equalsIgnoreCase("on")) {
            PistonDupe.enabled = true;
            sender.sendMessage(Configvar.config.getString("prefix") + "Dupe enabled.");
        } else if (args[0].equalsIgnoreCase("off")) {
            PistonDupe.enabled = false;
            sender.sendMessage(Configvar.config.getString("prefix") + "Dupe disabled.");
        } else {
            sender.sendMessage(Configvar.config.getString("prefix") + "/dupe <on|off>");
        }
        return true;
    }
}
