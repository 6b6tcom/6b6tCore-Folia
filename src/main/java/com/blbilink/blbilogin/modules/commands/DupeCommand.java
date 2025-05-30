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
        PistonDupe.enabled = !PistonDupe.enabled;
        String status = PistonDupe.enabled ? "enabled" : "disabled";
        sender.sendMessage(Configvar.config.getString("prefix") + "Dupe " + status + ".");
        return true;
    }
}
