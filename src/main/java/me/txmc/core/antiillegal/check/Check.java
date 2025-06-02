package me.txmc.core.antiillegal.check;

import org.bukkit.inventory.ItemStack;

/**
 * Base interface for item checks in 6b6t anti-illegal.
 */
public interface Check {
    boolean check(ItemStack item);

    boolean shouldCheck(ItemStack item);

    void fix(ItemStack item);
}
