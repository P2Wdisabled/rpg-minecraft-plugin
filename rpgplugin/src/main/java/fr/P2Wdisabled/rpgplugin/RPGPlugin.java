package fr.P2Wdisabled.rpgplugin;

import org.bukkit.plugin.java.JavaPlugin;

import fr.P2Wdisabled.rpgplugin.armors.ArmorManager;
import fr.P2Wdisabled.rpgplugin.armors.InfinityDeath;
import fr.P2Wdisabled.rpgplugin.body.PlayerBodyManager;
import fr.P2Wdisabled.rpgplugin.listeners.EntityDamageListener;
import fr.P2Wdisabled.rpgplugin.listeners.EntitySpawnListener;
import fr.P2Wdisabled.rpgplugin.skills.GodProtection;
import fr.P2Wdisabled.rpgplugin.skills.RegenerationBoost;
import fr.P2Wdisabled.rpgplugin.skills.SkillManager;
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
    private SkillManager skillManager;
    private PlayerBodyManager bodyManager;
	
	@Override
    public void onEnable() {
        instance = this;
        statsManager = new StatsManager();
        skillManager = new SkillManager();
        bodyManager = new PlayerBodyManager();
        registerSkills();

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
    }public SkillManager getSkillManager() {
        return skillManager;
    }

    public PlayerBodyManager getBodyManager() {
        return bodyManager;
    }

    private void registerSkills() {
        // Créer les compétences
        GodProtection godProtection = new GodProtection(10, "Mythic Skill");
        RegenerationBoost regenerationBoost = new RegenerationBoost(1, "Common Skill");
        // Créer les autres compétences

        // Enregistrer les compétences dans le SkillManager
        skillManager.registerSkill(godProtection);
        skillManager.registerSkill(regenerationBoost);
        // Enregistrer les autres compétences

        // Enregistrer les compétences en tant que Listeners
        getServer().getPluginManager().registerEvents(godProtection, this);
        getServer().getPluginManager().registerEvents(regenerationBoost, this);
        // Enregistrer les autres compétences
    }
}