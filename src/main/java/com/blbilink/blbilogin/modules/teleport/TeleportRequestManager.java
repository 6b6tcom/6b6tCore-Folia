package com.blbilink.blbilogin.modules.teleport;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportRequestManager {
    public static class TeleportRequest {
        public final UUID requester;
        public final UUID target;
        public final boolean toTarget;
        public final long timestamp;

        public TeleportRequest(UUID requester, UUID target, boolean toTarget) {
            this.requester = requester;
            this.target = target;
            this.toTarget = toTarget;
            this.timestamp = System.currentTimeMillis();
        }
    }

    private static final Map<UUID, TeleportRequest> requests = new HashMap<>();
    private static final long EXPIRATION = 60 * 1000L; // 60 seconds

    public static void addRequest(UUID requester, UUID target, boolean toTarget) {
        requests.put(target, new TeleportRequest(requester, target, toTarget));
    }

    public static TeleportRequest getRequest(UUID target) {
        TeleportRequest req = requests.get(target);
        if (req != null && System.currentTimeMillis() - req.timestamp <= EXPIRATION) {
            return req;
        }
        requests.remove(target);
        return null;
    }

    public static void removeRequest(UUID target) {
        requests.remove(target);
    }

    public static void cancelRequests(UUID uuid) {
        requests.entrySet().removeIf(e ->
                e.getKey().equals(uuid) || e.getValue().requester.equals(uuid));
    }
}
