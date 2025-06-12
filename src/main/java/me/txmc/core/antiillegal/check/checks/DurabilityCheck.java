package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

/**
 * Validates that item damage values are within allowed ranges.
 */
public class DurabilityCheck implements Check {
    @Override
    public boolean check(ItemStack item) {
        if (!(item.getItemMeta() instanceof Damageable meta)) return false;
        int damage = meta.getDamage();
        return damage < 0 || damage > item.getType().getMaxDurability();
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return item.getType().getMaxDurability() > 0;
    }

    @Override
    public void fix(ItemStack item) {
        if (!(item.getItemMeta() instanceof Damageable meta)) return;
        int clamped = Math.min(Math.max(meta.getDamage(), 0), item.getType().getMaxDurability());
        meta.setDamage(clamped);
        item.setItemMeta(meta);
    }
}
