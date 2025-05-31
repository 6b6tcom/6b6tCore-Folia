package com.blbilink.blbilogin.modules.events;

import com.blbilink.blbilogin.vars.Configvar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Anti32kDamage implements Listener {
    private static final double DAMAGE_THRESHOLD = 100.0;

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        if (!Configvar.config.getBoolean("disable32kDamage")) return;

        if (event.getDamager() instanceof Player attacker) {
            ItemStack item = attacker.getInventory().getItemInMainHand();
            if (item != null && item.getEnchantments().values().stream().anyMatch(l -> l > 1000)) {
                event.setCancelled(true);
                attacker.sendMessage(Configvar.config.getString("prefix") + "32k weapons are disabled against players.");
                return;
            }
        }

        if (event.getDamage() > DAMAGE_THRESHOLD) {
            event.setCancelled(true);
        }
    }
}
