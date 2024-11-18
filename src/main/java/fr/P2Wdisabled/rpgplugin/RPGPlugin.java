package fr.P2Wdisabled.rpgplugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.P2Wdisabled.rpgplugin.armors.ArmorManager;
import fr.P2Wdisabled.rpgplugin.armors.InfinityDeath;
import fr.P2Wdisabled.rpgplugin.listeners.EntityDamageListener;
import fr.P2Wdisabled.rpgplugin.listeners.EntitySpawnListener;
import fr.P2Wdisabled.rpgplugin.stats.StatsManager;
import fr.P2Wdisabled.rpgplugin.tasks.ActionBarTask;
import fr.P2Wdisabled.rpgplugin.tasks.RegenerationTask;
import fr.P2Wdisabled.rpgplugin.weapons.DoranShield;
import fr.P2Wdisabled.rpgplugin.weapons.WeaponManager;

public class RPGPlugin extends JavaPlugin {
	

    private static RPGPlugin instance;
    private StatsManager statsManager;
    private WeaponManager weaponManager;
    private ArmorManager armorManager;
	
	@Override
    public void onEnable() {
        instance = this;
        statsManager = new StatsManager();

        armorManager = new ArmorManager();
        armorManager.registerArmor(new InfinityDeath(10, "Mythic"));
        
        
        
        weaponManager = new WeaponManager();

        weaponManager.registerWeapon(new DoranShield(10, "Mythic"));
        
        getServer().getPluginManager().registerEvents(new EntitySpawnListener(statsManager), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(statsManager), this);

        // Démarrage de la tâche de régénération
        new RegenerationTask(statsManager).runTaskTimer(this, 0L, 100L); // 100 ticks = 5 secondes
        new ActionBarTask(statsManager).runTaskTimer(this, 0L, 20L); // 100 ticks = 1 secondes
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin de statistiques désactivé !");
    }

    public static RPGPlugin getInstance() {
        return instance;
    }

    public StatsManager getStatsManager() {
        return statsManager;
    }

    public ArmorManager getArmorManager() {
        return armorManager;
    }
    
    public WeaponManager getWeaponManager() {
        return weaponManager;
    }
}
