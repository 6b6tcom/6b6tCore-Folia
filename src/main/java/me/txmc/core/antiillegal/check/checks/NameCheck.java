package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

/** Placeholder for NameCheck */
public class NameCheck implements Check {
    public NameCheck(ConfigurationSection section) {}
    @Override
    public boolean check(ItemStack item) { return false; }
    @Override
    public boolean shouldCheck(ItemStack item) { return false; }
    @Override
    public void fix(ItemStack item) {}
}
