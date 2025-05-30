package com.blbilink.blbilogin.modules.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftItemFrame;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftGlowItemFrame;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.GlowItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Piston dupe listener based on XYMB's implementation.
 * Credit: XYMB
 */
public class PistonDupe implements Listener {
    public static boolean enabled = false;
    private final Map<Location, Long> slowdowns = new HashMap<>();
    private static final long SLOWDOWN = 20 * 1000L;

    private ItemStack itemFrameStack, glowItemFrameStack;

    @EventHandler
    public void pistonRetract(BlockPistonRetractEvent e) {
        if (!enabled) return;
        int x = 0, y = e.getBlock().getY(), z = 0;
        if (e.getDirection() == BlockFace.NORTH) {
            z = 1;
        } else if (e.getDirection() == BlockFace.SOUTH) {
            z = -1;
        } else if (e.getDirection() == BlockFace.EAST) {
            x = -1;
        } else if (e.getDirection() == BlockFace.WEST) {
            x = 1;
        }
        x += e.getBlock().getX();
        z += e.getBlock().getZ();

        Location location = new Location(e.getBlock().getWorld(), x, y, z);
        long last = slowdowns.getOrDefault(location, 0L);
        long millis = System.currentTimeMillis();
        if (last + SLOWDOWN > millis) return;
        slowdowns.put(location, millis);

        for (var entity : e.getBlock().getWorld().getNearbyEntities(location, 1, 1, 1)) {
            ItemStack stack = null;
            ItemStack secondItem = null;
            if (entity.getType() == EntityType.ITEM_FRAME) {
                ItemFrame itemFrame = ((CraftItemFrame) entity).getHandle();
                stack = itemFrame.getItem();
                if (itemFrameStack == null) {
                    itemFrameStack = new ItemStack(Material.ITEM_FRAME);
                    itemFrameStack.setAmount(1);
                }
                secondItem = itemFrameStack;
            }
            if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                GlowItemFrame itemFrame = ((CraftGlowItemFrame) entity).getHandle();
                stack = itemFrame.getItem();
                if (glowItemFrameStack == null) {
                    glowItemFrameStack = new ItemStack(Material.GLOW_ITEM_FRAME);
                    glowItemFrameStack.setAmount(1);
                }
                secondItem = glowItemFrameStack;
            }
            if (stack == null) continue;
            Location newLocation = new Location(e.getBlock().getWorld(), x + 0.5, y, z + 0.5);

            ((CraftWorld) e.getBlock().getWorld()).dropItem(newLocation, CraftItemStack.asBukkitCopy(stack));
            ((CraftWorld) e.getBlock().getWorld()).dropItem(newLocation, CraftItemStack.asCraftCopy(secondItem));
            System.out.println("ITEM_FRAME_DUPE " + location);
            return;
        }
    }
}
