package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;

/**
 * Check for illegal NBT data on items.
 * <p>
 * Part of the 6b6t anti-illegal system.
 * </p>
 */
public class IllegalDataCheck implements Check {
    @Override
    public boolean check(ItemStack item) {
        return false;
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return false;
    }

    @Override
    public void fix(ItemStack item) {
        // no-op
    }
}
