package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Simple check for suspicious item meta tags.
 */
public class IllegalDataCheck implements Check {
    @Override
    public boolean check(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.getPersistentDataContainer().getKeys().size() > 50;
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && !meta.getPersistentDataContainer().isEmpty();
    }

    @Override
    public void fix(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().getKeys().forEach(key -> meta.getPersistentDataContainer().remove(key));
            item.setItemMeta(meta);
        }
    }
}
