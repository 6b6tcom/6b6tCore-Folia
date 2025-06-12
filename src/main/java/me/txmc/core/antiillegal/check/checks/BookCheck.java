package me.txmc.core.antiillegal.check.checks;

import me.txmc.core.antiillegal.check.Check;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

/**
 * Limits book page counts and page size.
 */
public class BookCheck implements Check {
    private static final int MAX_PAGES = 50;
    private static final int MAX_PAGE_LENGTH = 256;

    @Override
    public boolean check(ItemStack item) {
        if (!(item.getItemMeta() instanceof BookMeta meta)) return false;
        if (meta.getPageCount() > MAX_PAGES) return true;
        return meta.getPages().stream().anyMatch(p -> p.length() > MAX_PAGE_LENGTH);
    }

    @Override
    public boolean shouldCheck(ItemStack item) {
        return item.getItemMeta() instanceof BookMeta;
    }

    @Override
    public void fix(ItemStack item) {
        if (!(item.getItemMeta() instanceof BookMeta meta)) return;
        while (meta.getPageCount() > MAX_PAGES) {
            meta.removePage(meta.getPageCount());
        }
        for (int i = 1; i <= meta.getPageCount(); i++) {
            String page = meta.getPage(i);
            if (page.length() > MAX_PAGE_LENGTH) {
                meta.setPage(i, page.substring(0, MAX_PAGE_LENGTH));
            }
        }
        item.setItemMeta(meta);
    }
}
