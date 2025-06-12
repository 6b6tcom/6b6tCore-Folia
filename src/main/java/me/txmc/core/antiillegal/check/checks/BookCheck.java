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
        java.util.List<String> pages = new java.util.ArrayList<>(meta.getPages());
        while (pages.size() > MAX_PAGES) {
            pages.remove(pages.size() - 1);
        }
        for (int i = 0; i < pages.size(); i++) {
            String page = pages.get(i);
            if (page.length() > MAX_PAGE_LENGTH) {
                pages.set(i, page.substring(0, MAX_PAGE_LENGTH));
            }
        }
        meta.setPages(pages);
        item.setItemMeta(meta);
    }
}
