package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DupeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String msg = "Use a chest boat, load it with items, ride it, then dismount. " +
                "All contents will duplicate and there is no cooldown.";
        sender.sendMessage(Configvar.config.getString("prefix") + msg);
        return true;
    }
}
