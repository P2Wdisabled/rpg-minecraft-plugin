package fr.P2Wdisabled.rpgplugin.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.P2Wdisabled.rpgplugin.stats.Stats;
import fr.P2Wdisabled.rpgplugin.stats.StatsManager;
import fr.P2Wdisabled.rpgplugin.utils.Utils;

public class EntityDamageListener implements Listener {

    private StatsManager statsManager;

    public EntityDamageListener(StatsManager statsManager) {
        this.statsManager = statsManager;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity damagedEntity = event.getEntity();

        Stats stats = statsManager.getStats(damagedEntity);

        // Annuler les dégâts par défaut de Minecraft
        event.setCancelled(true);

        // Calcul des dégâts après application de la défense
        double damage = event.getDamage();
        double finalDamage = damage * (100.0 / (100.0 + stats.getDefense()));

        // Réduire la santé virtuelle en fonction des dégâts
        stats.setHealth(stats.getHealth() - (int) finalDamage);

        // Vérifier si l'entité est morte virtuellement
        if (stats.getHealth() <= 0) {
            // Gérer la mort virtuelle de l'entité
            statsManager.removeStats(damagedEntity);
            // Vous pouvez ajouter ici des effets visuels ou des messages
            if (damagedEntity instanceof Player) {
                ((Player) damagedEntity).sendMessage("Vous êtes mort !");
            }
        } else {
            // Si c'est un joueur, mettre à jour son ActionBar
            if (damagedEntity instanceof Player) {
                Player player = (Player) damagedEntity;
                Utils.sendActionBar(player, "§cSanté: " + stats.getHealth() + " / " + stats.getMaxHealth() + " §bMana: " + stats.getMana() + " / " + stats.getMaxMana());
            }
        }
    }
}
