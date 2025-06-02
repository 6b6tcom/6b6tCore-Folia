package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;

/** Placeholder for DurabilityCheck */
public class DurabilityCheck implements Check {
    @Override
    public boolean check(ItemStack item) { return false; }
    @Override
    public boolean shouldCheck(ItemStack item) { return false; }
    @Override
    public void fix(ItemStack item) {}
}
