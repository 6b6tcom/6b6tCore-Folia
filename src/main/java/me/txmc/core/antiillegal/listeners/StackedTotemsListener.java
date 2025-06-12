package me.txmc.core.antiillegal.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * Prevents stacked totems from being consumed at once.
 */
public class StackedTotemsListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onConsume(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() == Material.TOTEM_OF_UNDYING && event.getItem().getAmount() > 1) {
            event.getItem().setAmount(1);
        }
    }
}
