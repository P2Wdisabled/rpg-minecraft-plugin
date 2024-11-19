package fr.P2Wdisabled.rpgplugin.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.P2Wdisabled.rpgplugin.stats.Stats;
import fr.P2Wdisabled.rpgplugin.stats.StatsManager;
import fr.P2Wdisabled.rpgplugin.utils.Utils;

public class ActionBarTask  extends BukkitRunnable {

    private StatsManager statsManager;

    public ActionBarTask(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @Override
    public void run() {
        // Parcours de toutes les entités enregistrées dans le StatsManager
        for (Stats stats : statsManager.getAllStats()) {
            // Si l'entité est un joueur, on peut afficher ses stats
            if (stats.getEntity() instanceof Player) {
                Player player = (Player) stats.getEntity();
                // Afficher les stats via l'ActionBar
                Utils.sendActionBar(player, "§c❤ Santé: " + stats.getHealth() + " / " + stats.getMaxHealth() + " §bMana: " + stats.getMana() + " / " + stats.getMaxMana());
            }
        }
    }
}