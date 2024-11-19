package fr.P2Wdisabled.rpgplugin.body;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerBodyManager {

    private Map<UUID, BodyParts> playerBodies = new HashMap<>();

    public BodyParts getBodyParts(UUID playerId) {
        return playerBodies.computeIfAbsent(playerId, k -> new BodyParts());
    }
}
