package me.txmc.core.antiillegal.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Listener that prevents players from dealing excessive damage.
 * Part of the 6b6t anti-illegal suite.
 */
public class AttackListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (event.getDamage() > 30D) {
            // Limit damage to prevent 32k weapons from one-shotting players.
            // Cancelling the event and damaging the attacker could lead to
            // inconsistent behaviour when other plugins listen to the same
            // event.  Simply cap the damage to a safe value instead.
            event.setDamage(30D);
        }
    }
}
