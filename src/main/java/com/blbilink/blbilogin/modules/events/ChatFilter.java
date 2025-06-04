package com.blbilink.blbilogin.modules.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChatFilter implements Listener {
    private static final String[] BLOCKED_TLDS = {".com", ".org", ".cc", ".net"};
    private static final String[] BLOCKED_WORDS = {"jonarchy"};

    private final Map<UUID, String> lastMessages = new HashMap<>();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String lower = message.toLowerCase();

        // Block links containing specific TLDs
        for (String tld : BLOCKED_TLDS) {
            if (lower.contains(tld)) {
                event.setCancelled(true);
                player.sendMessage("Links are not allowed in chat.");
                return;
            }
        }

        // Block specific offensive words
        for (String word : BLOCKED_WORDS) {
            if (lower.contains(word)) {
                event.setCancelled(true);
                player.sendMessage("That word is not allowed in chat.");
                return;
            }
        }

        // Duplicate message detection with basic similarity check
        String sanitized = lower.replaceAll("[^a-z0-9]", "");
        String previous = lastMessages.get(player.getUniqueId());
        if (previous != null && isSimilar(sanitized, previous)) {
            event.setCancelled(true);
            player.sendMessage("Please avoid sending duplicate messages.");
            return;
        }

        lastMessages.put(player.getUniqueId(), sanitized);
    }

    private boolean isSimilar(String a, String b) {
        if (a.equals(b)) return true;
        return levenshteinDistance(a, b) <= 2;
    }

    private int levenshteinDistance(String a, String b) {
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}
