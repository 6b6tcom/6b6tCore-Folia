package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.teleport.TeleportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class TpaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        if (args.length != 1) {
            player.sendMessage("/tpa <player>");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage("Player not online");
            return true;
        }
        TeleportManager.addRequest(target, player, false);
        player.sendMessage("Teleport request sent to " + target.getName());
        target.sendMessage(player.getName() + " wants to teleport to you. Use /tpaccept to allow.");
        return true;
    }
}
