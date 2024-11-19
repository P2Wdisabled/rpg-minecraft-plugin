package fr.P2Wdisabled.rpgplugin.mobs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Endermite;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import fr.P2Wdisabled.rpgplugin.RPGPlugin;

public class BabyHydra extends CustomMob implements Listener {

    private Map<String, Double> lootTable = new HashMap<>();
    private MobManager mobmanager;
    private static final Random RANDOM = new Random(); // Instance unique de Random
    public BabyHydra() {
        super("Baby-Hydra", 1000, 200000, 100000, 500, 1000);

        // Initialiser le loot table
        lootTable.put("poisoned_dagger", 15.0); // 15%
        lootTable.put("guild_house_badge", 10.0); // 10%
        // Rien : 75% (implicitement)
    }

    @SuppressWarnings("deprecation")
	public LivingEntity spawn(Location location) {
        Endermite endermite = location.getWorld().spawn(location, Endermite.class);
        endermite.setCustomName(ChatColor.RED + name);
        endermite.setCustomNameVisible(true);
        endermite.setMaxHealth(health);
        endermite.setHealth(health);
        endermite.setMetadata("customMob", new FixedMetadataValue(RPGPlugin.getInstance(), "baby_hydra"));

        // Ajouter des comportements personnalisés
        setCustomAI(endermite);

        // Enregistrer cet objet en tant que Listener
        RPGPlugin.getInstance().getServer().getPluginManager().registerEvents(this, RPGPlugin.getInstance());

        return endermite;
    }

    private void setCustomAI(Endermite endermite) {
        // Lancer une tâche répétée pour chercher le joueur le plus proche
        new BukkitRunnable() {
            @Override
            public void run() {
                if (endermite.isDead()) {
                    cancel();
                    return;
                }

                Player target = getNearestPlayer(endermite);
                if (target != null) {
                    endermite.setTarget(target);
                }
            }
        }.runTaskTimer(RPGPlugin.getInstance(), 0L, 20L); // Exécuter toutes les secondes
    }

    private Player getNearestPlayer(Endermite endermite) {
    	Player nearest = null;
    	double radius = 20; 
    	double nearestDistance = 20;
    	for (Player player : endermite.getWorld().getPlayers()) {
            double distance = player.getLocation().distance(endermite.getLocation());
            if (distance <= radius && (nearest == null || distance < nearestDistance)) {
                nearest = player;
                nearestDistance = distance;
            }
        }
		return nearest;
    	
    }
    private Map<UUID, Set<UUID>> mobDamagers = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity damaged = event.getEntity();
        Entity damager = event.getDamager();
        event.setDamage(0);
        // Vérifier si le mob endommagé est une entité vivante
        if (!(damaged instanceof LivingEntity)) return;

        // Vérifier si l'attaquant est un joueur
        if (damager instanceof Player) {
            UUID damagedUUID = damaged.getUniqueId();
            UUID damagerUUID = damager.getUniqueId();

            // Récupérer ou créer l'ensemble des joueurs qui ont endommagé ce mob
            Set<UUID> damagers = mobDamagers.getOrDefault(damagedUUID, new HashSet<>());
            damagers.add(damagerUUID);
            mobDamagers.put(damagedUUID, damagers);

            // Le dernier joueur qui a infligé des dégâts est 'damager'
            Player lastDamager = (Player) damager;

            // Pour récupérer tous les joueurs qui ont endommagé ce mob
            Set<UUID> allDamagersUUID = mobDamagers.get(damagedUUID);

            // Calculer la santé finale après les dégâts
            LivingEntity livingDamaged = (LivingEntity) damaged;
            double finalHealth = livingDamaged.getHealth() - event.getFinalDamage();

            if (finalHealth <= 0) {
                // Le mob va mourir, distribuer les récompenses
                for (UUID playerUUID : allDamagersUUID) {
                    Player player = Bukkit.getPlayer(playerUUID);
                    if (player != null && player.isOnline()) {
                        ItemStack drop = getRandomLoot();
                        player.getInventory().addItem(drop);
                    }
                }

                // Nettoyer la liste des damagers pour ce mob
                mobDamagers.remove(damagedUUID);
                ItemStack drop = getRandomLoot();
                lastDamager.getInventory().addItem(drop);
            }
        }
    }
    
    
    private ItemStack getRandomLoot() {
        double chance = RANDOM.nextDouble() * 100; // Génère un nombre entre 0 (inclus) et 100 (exclus)
        double cumulative = 0;

        for (Map.Entry<String, Double> entry : lootTable.entrySet()) {
            cumulative += entry.getValue();
            if (chance < cumulative) { // Utilisation de < au lieu de <=
                String lootName = entry.getKey();
                return mobmanager.giveLoot(lootName); // Utilisation de la méthode giveLoot pour créer l'item
            }
        }
        // Rien n'est drop si aucune condition n'est remplie
        return null;
    }
}
