package com.blbilink.blbilogin.modules.dupe;

import org.bukkit.Material;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChestBoatDupeListener implements Listener {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownMillis;

    public ChestBoatDupeListener(long cooldownSeconds) {
        this.cooldownMillis = cooldownSeconds * 1000L;
    }

    @EventHandler
    public void onVehicleExit(VehicleExitEvent event) {
        if (!(event.getExited() instanceof Player player)) return;
        if (!(event.getVehicle() instanceof ChestBoat chestBoat)) return;

        Inventory inv = chestBoat.getInventory();

        long last = cooldowns.getOrDefault(player.getUniqueId(), 0L);
        long now = System.currentTimeMillis();
        if (now - last < cooldownMillis) return;

        for (ItemStack item : inv.getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                player.getWorld().dropItemNaturally(player.getLocation(), item.clone());
            }
        }

        cooldowns.put(player.getUniqueId(), now);
    }
}
