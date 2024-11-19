package fr.P2Wdisabled.rpgplugin.stats;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class StatsManager {

    protected Map<UUID, Stats> statsMap = new ConcurrentHashMap<>();

    // Récupère les stats d'une entité, ou les crée si inexistantes
    public Stats getStats(Entity entity) {
        return statsMap.computeIfAbsent(entity.getUniqueId(), k -> new Stats(entity));
    }

    // Crée les stats pour une entité
    public void createStats(Entity entity) {
        statsMap.putIfAbsent(entity.getUniqueId(), new Stats(entity));
    }

    // Supprime les stats d'une entité
    public void removeStats(Entity entity) {
        statsMap.remove(entity.getUniqueId());
    }

    // Récupère toutes les stats
    public Collection<Stats> getAllStats() {
        return statsMap.values();
    }

    // Méthode pour ajouter de l'XP à un joueur
    public void addXp(Player player, int amount) {
        Stats stats = getStats(player);
        stats.setXp(stats.getXp() + amount);
    }

    // Méthode pour ajouter de l'XP spéciale à un joueur (pour le système de Tiers)
    public void addSpecialXp(Player player, int amount) {
        Stats stats = getStats(player);
        stats.setSpecialXp(stats.getSpecialXp() + amount);
    }

    // Méthode pour recalculer les statistiques du joueur (pour le système de niveaux)
    public void recalculatePlayerStats(Player player) {
        Stats stats = getStats(player);
        stats.recalculateLevel();
        stats.recalculatePoints();
        stats.recalculateStatPercentageIncrease();
        stats.recalculateTier();
        stats.recalcStats();
    }

    // Méthode pour ajouter des points totaux à un joueur
    public void addTotalPoints(Player player, int nbPoints) {
        Stats stats = getStats(player);
        stats.setTotalPoints(stats.getTotalPoints() + nbPoints);
    }

    // Méthode pour allouer des points à une statistique
    public boolean allocatePoints(Player player, String stat, int nbPoints) {
        Stats stats = getStats(player);
        return stats.allocatePoints(stat, nbPoints);
    }
}
