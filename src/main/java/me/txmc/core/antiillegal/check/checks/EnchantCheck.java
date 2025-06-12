package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * Ensures enchantments do not exceed their maximum allowed level.
 */
public class EnchantCheck implements Check {
    @Override
    public boolean check(ItemStack item) {
        return item.getEnchantments().entrySet().stream()
                .anyMatch(e -> e.getValue() > e.getKey().getMaxLevel());
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return !item.getEnchantments().isEmpty();
    }

    @Override
    public void fix(ItemStack item) {
        for (var entry : item.getEnchantments().entrySet()) {
            Enchantment ench = entry.getKey();
            int level = Math.min(entry.getValue(), ench.getMaxLevel());
            item.addEnchantment(ench, level);
        }
    }
}
