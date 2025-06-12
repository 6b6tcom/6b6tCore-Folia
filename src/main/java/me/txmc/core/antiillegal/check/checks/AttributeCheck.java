package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collection;

/**
 * Limits attribute modifier values to sane ranges.
 */
public class AttributeCheck implements Check {
    private static final double MAX_VALUE = 2048D;

    @Override
    public boolean check(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;
        Collection<AttributeModifier> modifiers = meta.getAttributeModifiers() == null ? null : meta.getAttributeModifiers().values();
        if (modifiers == null) return false;
        for (AttributeModifier mod : modifiers) {
            if (Math.abs(mod.getAmount()) > MAX_VALUE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasAttributeModifiers();
    }

    @Override
    public void fix(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getAttributeModifiers() == null) return;
        for (Attribute attr : meta.getAttributeModifiers().keySet()) {
            for (AttributeModifier mod : meta.getAttributeModifiers(attr)) {
                if (Math.abs(mod.getAmount()) > MAX_VALUE) {
                    meta.removeAttributeModifier(attr, mod);
                }
            }
        }
        item.setItemMeta(meta);
    }
}
