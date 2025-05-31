package com.blbilink.blbilogin.modules.events;

import org.bukkit.entity.Wither;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class WitherSkullExplodeFix implements Listener {
    @EventHandler
    public void onWitherSkullExplode(EntityExplodeEvent event) {
        if (event.getEntity() instanceof WitherSkull skull) {
            if (skull.getShooter() instanceof Wither) {
                event.setYield(15.0F);
            }
        }
    }
}
