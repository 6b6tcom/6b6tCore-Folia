package me.txmc.core.antiillegal.listeners;

import me.txmc.core.antiillegal.AntiIllegalMain;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Miscellaneous listeners used by the anti-illegal system.
 */
public class MiscListeners implements Listener {
    private final AntiIllegalMain main;

    public MiscListeners(AntiIllegalMain main) {
        this.main = main;
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemSpawn(ItemSpawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        main.checkFixItem(item, null);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryMove(InventoryMoveItemEvent event) {
        ItemStack item = event.getItem();
        main.checkFixItem(item, event);
    }
}
