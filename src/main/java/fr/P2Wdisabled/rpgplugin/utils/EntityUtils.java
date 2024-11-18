package fr.P2Wdisabled.rpgplugin.utils;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EntityUtils {

    public static String getEntityName(Entity entity) {
        if (entity instanceof Player) {
            return ((Player) entity).getDisplayName();
        } else {
            return entity.getType().name();
        }
    }
}
