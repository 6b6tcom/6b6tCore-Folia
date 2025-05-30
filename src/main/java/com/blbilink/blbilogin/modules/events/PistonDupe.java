package com.blbilink.blbilogin.modules.events;

import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_20_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftGlowItemFrame;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftItemFrame;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonRetractEvent;

/**
 * Item frame piston dupe courtesy of XYMB
 */
public class PistonDupe implements Listener {
    public static boolean enabled = false;
    Object2LongOpenHashMap<Location> slowdowns = new Object2LongOpenHashMap<>();
    long SLOWDOWN = 20 * 1000;
    org.bukkit.inventory.ItemStack itemFrameStack, glowItemFrameStack;

    @EventHandler
    public void pistonRetract(BlockPistonRetractEvent e) {
        if (!enabled) return;
        int x = 0, y = e.getBlock().getY(), z = 0;
        BlockFace face = e.getDirection();
        if (face == BlockFace.NORTH) { x = 0; z = 1; }
        else if (face == BlockFace.SOUTH) { x = 0; z = -1; }
        else if (face == BlockFace.EAST) { x = -1; z = 0; }
        else if (face == BlockFace.WEST) { x = 1; z = 0; }
        x += e.getBlock().getX();
        z += e.getBlock().getZ();
        if (true || e.getBlock().getWorld().getBlockAt(x, y, z).getType() == Material.AIR) {
            Location location = new Location(e.getBlock().getWorld(), x, y, z);
            long last = slowdowns.getLong(location);
            long millis = System.currentTimeMillis();
            if (last + SLOWDOWN > millis) return;
            slowdowns.put(location, millis);
            for (var entity : e.getBlock().getWorld().getNearbyEntities(location, 1, 1, 1)) {
                ItemStack stack = null; org.bukkit.inventory.ItemStack secondItem = null;
                if (entity.getType() == EntityType.ITEM_FRAME) {
                    ItemFrame itemFrame = ((CraftItemFrame) entity).getHandle();
                    stack = itemFrame.getItem();
                    if (itemFrameStack == null) { itemFrameStack = new org.bukkit.inventory.ItemStack(Material.ITEM_FRAME); itemFrameStack.setAmount(1); }
                    secondItem = itemFrameStack;
                }
                if (entity.getType() == EntityType.GLOW_ITEM_FRAME) {
                    GlowItemFrame itemFrame = ((CraftGlowItemFrame) entity).getHandle();
                    stack = itemFrame.getItem();
                    if (glowItemFrameStack == null) { glowItemFrameStack = new org.bukkit.inventory.ItemStack(Material.GLOW_ITEM_FRAME); glowItemFrameStack.setAmount(1); }
                    secondItem = glowItemFrameStack;
                }
                if (stack == null) continue;
                Location newLocation = new Location(e.getBlock().getWorld(), x + 0.5, y, z + 0.5);
                ((CraftWorld) e.getBlock().getWorld()).dropItem(newLocation, CraftItemStack.asBukkitCopy(stack));
                ((CraftWorld) e.getBlock().getWorld()).dropItem(newLocation, CraftItemStack.asCraftCopy(secondItem));
                return;
            }
        }
    }
}
