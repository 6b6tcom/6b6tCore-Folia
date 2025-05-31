package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.teleport.TeleportManager;
import com.blbilink.blbilogin.modules.teleport.TeleportManager.TeleportRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class TpAcceptCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return true;
        TeleportRequest request = TeleportManager.removeRequest(player);
        if (request == null) {
            player.sendMessage("No pending requests");
            return true;
        }
        Player requester = request.requester;
        if (!requester.isOnline()) {
            player.sendMessage("Requester is not online");
            return true;
        }
        if (request.here) {
            // teleport target (player) to requester
            if (plugin.foliaUtil.isFolia) {
                player.getScheduler().run(plugin, task -> player.teleportAsync(requester.getLocation()), () -> {});
            } else {
                plugin.foliaUtil.runTask(plugin, t -> player.teleport(requester));
            }
        } else {
            // teleport requester to player
            if (plugin.foliaUtil.isFolia) {
                requester.getScheduler().run(plugin, task -> requester.teleportAsync(player.getLocation()), () -> {});
            } else {
                plugin.foliaUtil.runTask(plugin, t -> requester.teleport(player));
            }
        }
        player.sendMessage("Teleport request accepted");
        requester.sendMessage(player.getName() + " accepted your request");
        return true;
    }
}
