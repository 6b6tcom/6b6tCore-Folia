package com.blbilink.blbilogin.modules.commands;

import com.blbilink.blbilogin.modules.Sqlite;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.blbilink.blbilogin.BlbiLogin.plugin;

public class TpOfflineCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player) || !player.isOp()) {
            sender.sendMessage("Only operators can use this command");
            return true;
        }
        if (args.length != 1) {
            player.sendMessage("/tpoffline <username>");
            return true;
        }
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        Location loc = Sqlite.getSqlite().getLogoutLocation(target.getUniqueId().toString());
        if (loc == null) {
            player.sendMessage("No logout location for that player");
            return true;
        }
        if (plugin.foliaUtil.isFolia) {
            player.getScheduler().run(plugin, task -> player.teleportAsync(loc), () -> {});
        } else {
            plugin.foliaUtil.runTask(plugin, t -> player.teleport(loc));
        }
        return true;
    }
}
