package me.txmc.core.antiillegal.listeners;

import me.txmc.core.antiillegal.AntiIllegalMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Player related anti-illegal checks.
 */
public class PlayerListeners implements Listener {
    private final AntiIllegalMain main;

    public PlayerListeners(AntiIllegalMain main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        for (ItemStack item : event.getPlayer().getInventory().getContents()) {
            if (item != null) {
                main.checkFixItem(item, null);
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPickup(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Player)) return;
        ItemStack item = event.getItem().getItemStack();
        main.checkFixItem(item, event);
    }
}
