package com.blbilink.blbilogin.modules.teleport;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportManager {
    public static class TeleportRequest {
        public final Player requester;
        public final boolean here;
        public TeleportRequest(Player requester, boolean here) {
            this.requester = requester;
            this.here = here;
        }
    }

    private static final Map<UUID, TeleportRequest> requests = new ConcurrentHashMap<>();

    public static void addRequest(Player target, Player requester, boolean here) {
        requests.put(target.getUniqueId(), new TeleportRequest(requester, here));
    }

    public static TeleportRequest removeRequest(Player target) {
        return requests.remove(target.getUniqueId());
    }
}
