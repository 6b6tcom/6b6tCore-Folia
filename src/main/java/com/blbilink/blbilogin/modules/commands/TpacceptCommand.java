package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.teleport.TeleportRequestManager;
import com.blbilink.blbilogin.modules.teleport.TeleportRequestManager.TeleportRequest;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class TpacceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        TeleportRequest req = TeleportRequestManager.getRequest(player.getUniqueId());
        if (req == null) {
            player.sendMessage("No pending teleport requests");
            return true;
        }

        Player requester = Bukkit.getPlayer(req.requester);
        if (requester == null) {
            player.sendMessage("Requester is not online");
            TeleportRequestManager.removeRequest(player.getUniqueId());
            return true;
        }

        if (req.toTarget) {
            Location dest = player.getLocation();
            if (plugin.foliaUtil.isFolia) {
                requester.getScheduler().run(plugin, task -> requester.teleportAsync(dest), () -> {});
            } else {
                plugin.foliaUtil.runTask(plugin, t -> requester.teleport(dest));
            }
        } else {
            Location dest = requester.getLocation();
            if (plugin.foliaUtil.isFolia) {
                player.getScheduler().run(plugin, task -> player.teleportAsync(dest), () -> {});
            } else {
                plugin.foliaUtil.runTask(plugin, t -> player.teleport(dest));
            }
        }

        TeleportRequestManager.removeRequest(player.getUniqueId());
        player.sendMessage("Teleport request accepted");
        requester.sendMessage(player.getName() + " accepted your teleport request");
        return true;
    }
}
