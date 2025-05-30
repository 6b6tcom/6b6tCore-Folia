package com.blbilink.blbilogin.modules.events;

import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockPluginsCommand implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage().toLowerCase();
        if (msg.equalsIgnoreCase("/pl") || msg.equalsIgnoreCase("/plugins")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Configvar.config.getString("prefix") + "This command is disabled.");
        }
    }
}
