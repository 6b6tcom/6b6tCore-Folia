package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Enforces length limits on item display names.
 */
public class NameCheck implements Check {
    private final int maxLength;

    public NameCheck(ConfigurationSection section) {
        this.maxLength = section.getInt("MaxNameLength", 64);
    }

    @Override
    public boolean check(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasDisplayName() && meta.getDisplayName().length() > maxLength;
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasDisplayName();
    }

    @Override
    public void fix(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            String name = meta.getDisplayName();
            if (name.length() > maxLength) {
                meta.setDisplayName(name.substring(0, maxLength));
                item.setItemMeta(meta);
            }
        }
    }
}
