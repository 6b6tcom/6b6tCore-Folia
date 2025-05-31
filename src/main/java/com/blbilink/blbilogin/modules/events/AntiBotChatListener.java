package com.blbilink.blbilogin.modules.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.*;
import java.util.regex.Pattern;

public class AntiBotChatListener implements Listener {
    private final Map<String, Set<String>> messageSenders = new HashMap<>();
    private final Map<String, Long> messageTime = new HashMap<>();
    private static final long WINDOW_MS = 5000; // 5 seconds
    private static final int THRESHOLD = 3;
    private static final Pattern TLD_PATTERN = Pattern.compile("\\.(com|net|org|info|biz|ru|online|site|xyz)", Pattern.CASE_INSENSITIVE);

    @EventHandler(priority = EventPriority.HIGH)
    public void onChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage().toLowerCase(Locale.ROOT).trim();

        if (TLD_PATTERN.matcher(msg).find()) {
            event.setCancelled(true);
            return;
        }

        long now = System.currentTimeMillis();
        cleanOldEntries(now);

        messageSenders.computeIfAbsent(msg, k -> new HashSet<>()).add(event.getPlayer().getName());
        messageTime.putIfAbsent(msg, now);

        if (messageSenders.get(msg).size() >= THRESHOLD) {
            event.setCancelled(true);
        }
    }

    private void cleanOldEntries(long now) {
        Iterator<Map.Entry<String, Long>> it = messageTime.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Long> entry = it.next();
            if (now - entry.getValue() > WINDOW_MS) {
                it.remove();
                messageSenders.remove(entry.getKey());
            }
        }
    }
}
