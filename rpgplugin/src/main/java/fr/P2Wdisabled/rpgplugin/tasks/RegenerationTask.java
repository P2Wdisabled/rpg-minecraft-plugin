package fr.P2Wdisabled.rpgplugin.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import fr.P2Wdisabled.rpgplugin.stats.Stats;
import fr.P2Wdisabled.rpgplugin.stats.StatsManager;

public class RegenerationTask extends BukkitRunnable {

    private StatsManager statsManager;

    public RegenerationTask(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public void run() {
        // Parcours de toutes les entités enregistrées dans le StatsManager
        for (Stats stats : statsManager.getAllStats()) {
            // Régénération du mana et de la santé
            stats.setMana(stats.getMana() + 1); // Régénère 1 point de mana
            stats.setHealth(stats.getHealth() + 1); // Régénère 1 point de santé

        }
    }
}
