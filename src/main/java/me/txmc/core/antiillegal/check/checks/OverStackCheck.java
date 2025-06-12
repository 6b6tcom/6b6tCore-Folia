package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;

/**
 * Ensures stack sizes do not exceed the item's max stack amount.
 */
public class OverStackCheck implements Check {
    @Override
    public boolean check(ItemStack item) {
        return item.getAmount() > item.getMaxStackSize();
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return item.getMaxStackSize() > 0;
    }

    @Override
    public void fix(ItemStack item) {
        int fixed = Math.min(item.getAmount(), item.getMaxStackSize());
        item.setAmount(fixed);
    }
}
