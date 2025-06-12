package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionMeta;

/**
 * Checks for extreme potion effects and removes them.
 */
public class PotionCheck implements Check {
    private static final int MAX_AMPLIFIER = 10;
    private static final int MAX_DURATION = 20 * 60 * 10; // 10 minutes

    @Override
    public boolean check(ItemStack item) {
        if (!(item.getItemMeta() instanceof PotionMeta meta)) return false;
        for (PotionEffect effect : meta.getCustomEffects()) {
            if (effect.getAmplifier() > MAX_AMPLIFIER || effect.getDuration() > MAX_DURATION) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return item.getItemMeta() instanceof PotionMeta meta && !meta.getCustomEffects().isEmpty();
    }

    @Override
    public void fix(ItemStack item) {
        if (!(item.getItemMeta() instanceof PotionMeta meta)) return;
        for (PotionEffect effect : meta.getCustomEffects()) {
            if (effect.getAmplifier() > MAX_AMPLIFIER || effect.getDuration() > MAX_DURATION) {
                meta.removeCustomEffect(effect.getType());
            }
        }
        item.setItemMeta(meta);
    }
}
