package fr.P2Wdisabled.rpgplugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.P2Wdisabled.rpgplugin.stats.StatsManager;

public class EntitySpawnListener implements Listener {

    private StatsManager statsManager;

    public EntitySpawnListener(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        CreatureSpawnEvent.SpawnReason reason = event.getSpawnReason();
        if (reason != CreatureSpawnEvent.SpawnReason.COMMAND || reason != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            event.setCancelled(true);
        } else {
            Entity entity = event.getEntity();
            statsManager.createStats(entity);
            String entityName = entity.getName();
            Bukkit.broadcastMessage(entityName + " a spawné.");
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        statsManager.createStats(player);
        statsManager.recalculatePlayerStats(player);

        String playerName = player.getName();
        Bukkit.broadcastMessage(playerName + " a rejoint le serveur.");
    }
}
